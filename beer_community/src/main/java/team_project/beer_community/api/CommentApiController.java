package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.CommentDto;
import team_project.beer_community.dto.ReCommentDto;
import team_project.beer_community.dto.WriteCommentDto;
import team_project.beer_community.dto.WriteReCommentDto;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.CommentService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final BeerService beerService;
    private final CommentService commentService;

    @PostMapping("/api/comments/write-comment")
    public ResponseEntity<Void> writeComment(
            @RequestBody @Valid WriteCommentDto writeCommentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        try{
            User user = principalDetails.getUser();
            Comment comment = new Comment(user, writeCommentDto.getContent(), writeCommentDto.getPoint());
            commentService.join(comment);
            beerService.addComment(writeCommentDto.getBeerId(), comment); //이 안에서 beer-comment/user-comment 모두 매핑
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
        User user = principalDetails.getUser();
        if(writeReCommentDto.getParentId() != null) {
            Comment comment = new Comment(user, writeReCommentDto.getContent(), writeReCommentDto.getParentId()); // 대댓글일 경우 parentId 필요
            commentService.join(comment);
            beerService.addComment(writeReCommentDto.getBeerId(), comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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

    @GetMapping("/api/comments/{beerId}") // 맥주상세보기 들어갔을때 댓글들 랜더링
    public WrapperClass showComments(
            @PathVariable("beerId") Long beerId){
        //fetch join 사용, comment와 user를 한번에 조회
        List<Comment> comments = commentService.findAllWithUser(beerId);
        List<CommentDto> commentDtos = comments.stream()
                .map(c -> new CommentDto(c.getUser(), c))
                .collect(Collectors.toList());
        return new WrapperClass(commentDtos);
    }


    @GetMapping("/api/recomments/{commentId}")  //drop down 눌렀을 경우
    public WrapperClass showReComments(
            @PathVariable("commentId") Long parentCommentId){
        List<Comment> recomments = commentService.findAllRecomments(parentCommentId);
        List<ReCommentDto> reCommentDtos = recomments.stream()
                .map(r -> new ReCommentDto(r.getUser(), r))
                .collect(Collectors.toList());
        return new WrapperClass(reCommentDtos);
    }
}
