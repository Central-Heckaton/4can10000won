package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.*;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.CommentService;
import team_project.beer_community.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentApiController {
    private final BeerService beerService;
    private final CommentService commentService;
    private final UserService userService;

    @PostMapping("/api/comments/write-comment")
    public ResponseEntity<Void> writeComment(
            @RequestBody WriteCommentDto writeCommentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(principalDetails == null || principalDetails.getUser() == null){
            log.info("user logouted fail to writeComment() | 403 FORBIDDEN");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // 403
        }
        try{
            User user = userService.getUserWithInitializedComments(principalDetails.getUser().getId());
            Comment comment = new Comment(user, writeCommentDto.getContent(), writeCommentDto.getPoint());
            commentService.join(comment);
            beerService.addComment(writeCommentDto.getBeerId(), comment); //이 안에서 beer-comment/user-comment 모두 매핑
            System.out.println("try/comment = " + comment);
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        } catch (Exception exception){
            System.out.println("writeComment/exception = " + exception);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/comments/write-recomment") //대댓글에도 맥주와 매핑 시킨 경우
    public ResponseEntity<Void> writeReComment(
            @RequestBody @Valid WriteReCommentDto writeReCommentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userService.getUserWithInitializedComments(principalDetails.getUser().getId());
        if(writeReCommentDto.getParentId() != null) {
            Comment comment = new Comment(writeReCommentDto.getParentId(), user, writeReCommentDto.getContent()); // 대댓글일 경우 parentId 필요
            commentService.join(comment);
            beerService.addComment(writeReCommentDto.getBeerId(), comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/api/comments/updated-comment/{commentId}") // React와 연동안됨(403 Forbidden)
    public ResponseEntity<Void> updateComment(
            @PathVariable("commentId") Long commentId,
            @RequestBody UpdateCommentDto updateCommentDto){
        commentService.updateContent(commentId, updateCommentDto.getContent());
        commentService.updatePoint(commentId, updateCommentDto.getPoint());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/api/comments/updated-comment/{commentId}")
    public ResponseEntity<Void> updateComment_post(
            @PathVariable("commentId") Long commentId,
            @RequestBody UpdateCommentDto updateCommentDto){
        commentService.updateContent(commentId, updateCommentDto.getContent());
        commentService.updatePoint(commentId, updateCommentDto.getPoint());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/api/comments/delete-comment/{commentId}") //댓글, 대댓글 삭제 기능(부모일 경우 관련 대댓글도 모두 삭제)
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long commentId){
        try{
            Comment comment = commentService.findOne(commentId);
            if (comment.getParentId() == 0){  //부모 댓글일 경우(대댓글이 존재)
                List<Comment> reComments = commentService.findAllRecomments(commentId);
                for (Comment reComment : reComments) {
                    commentService.delete(reComment);
                }
            }
            commentService.delete(comment); //자식 댓글(대댓글)을 삭제 후 부모 댓글 삭제(순서는 상관없을 거 같긴 함)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //delete 요청일 경우 성공시 204를 return
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/comments/delete-comment/{commentId}") //댓글, 대댓글 삭제 기능(부모일 경우 관련 대댓글도 모두 삭제)
    public ResponseEntity<Void> deleteComment_get(@PathVariable("commentId") Long commentId){
        try{
            Comment comment = commentService.findOne(commentId);
            if (comment.getParentId() == 0){  //부모 댓글일 경우(대댓글이 존재)
                List<Comment> reComments = commentService.findAllRecomments(commentId);
                for (Comment reComment : reComments) {
                    commentService.delete(reComment);
                }
            }
            commentService.delete(comment); //자식 댓글(대댓글)을 삭제 후 부모 댓글 삭제(순서는 상관없을 거 같긴 함)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //delete 요청일 경우 성공시 204를 return
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/comments/{beerId}") // 맥주에 해당하는 댓글들 랜더링
    public ResponseEntity showComments(
            @PathVariable("beerId") Long beerId,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        //fetch join 사용, comment와 user를 한번에 조회
        if(principalDetails == null || principalDetails.getUser() == null){
            List<Comment> comments = commentService.findAllWithBeer(beerId);
            for (Comment comment: comments) {
                System.out.println("comment = " + comment);
            }
            List<CommentDto> commentDtos = comments
                    .stream()
                    .map(c -> new CommentDto(c.getUser(), c ,commentService.findRecommentsCount(c.getId())))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new WrapperClassWithUserId<>(-1L, commentDtos));
        } else {
            Long userId = principalDetails.getUser().getId();
            List<Comment> comments = commentService.findAllWithUser(beerId);
            List<CommentDto> commentDtos = comments.stream()
                    .map(c -> new CommentDto(c.getUser(), c, commentService.findRecommentsCount(c.getId())))
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(new WrapperClassWithUserId<>(userId, commentDtos));
        }
    }


    @GetMapping("/api/recomments/{commentId}")  //drop down 눌렀을 경우
    public ResponseEntity showReComments(
            @PathVariable("commentId") Long parentCommentId,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        Long userId = principalDetails.getUser().getId();
        List<Comment> recomments = commentService.findAllRecomments(parentCommentId);
        List<ReCommentDto> reCommentDtos = recomments.stream()
                .map(r -> new ReCommentDto(r.getUser(), r))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new WrapperClassWithUserId<>(userId, reCommentDtos));
    }

    @GetMapping("/api/comments/{beerId}/{userId}") // 맥주에 해당하는 댓글들 랜더링
    public ResponseEntity showComments_test(
            @PathVariable("beerId") Long beerId,
            @PathVariable("userId") Long userId){
        //fetch join 사용, comment와 user를 한번에 조회
        List<Comment> comments = commentService.findAllWithUser(beerId);
        List<CommentDto> commentDtos = comments.stream()
                .map(c -> new CommentDto(c.getUser(), c, commentService.findRecommentsCount(c.getId())))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new WrapperClassWithUserId<>(userId, commentDtos));
    }


    @GetMapping("/api/recomments/{commentId}/{userId}")  //drop down 눌렀을 경우
    public ResponseEntity showReComments_test(
            @PathVariable("commentId") Long parentCommentId,
            @PathVariable("userId") Long userId){
        List<Comment> recomments = commentService.findAllRecomments(parentCommentId);
        List<ReCommentDto> reCommentDtos = recomments.stream()
                .map(r -> new ReCommentDto(r.getUser(), r))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(new WrapperClassWithUserId<>(userId, reCommentDtos));
    }
}
