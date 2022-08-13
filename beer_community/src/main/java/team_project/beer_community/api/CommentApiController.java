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
import team_project.beer_community.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final BeerService beerService;
    private final CommentService commentService;
    private final UserService userService;

    //beerId가 잘못들어오는 경우는 없으므로 무조건 OK return
    @PostMapping("/api/comments/write-comment")
    public ResponseEntity<Void> writeComment(
            @RequestBody @Valid WriteCommentDto writeCommentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        Comment comment = new Comment(user, writeCommentDto.getContent(), writeCommentDto.getPoint());
        beerService.addComment(writeCommentDto.getBeerId(), comment); //이 안에서 beer-comment/user-comment 모두 매핑
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/write-recomment") //대댓글에도 맥주와 매핑 시킨 경우
    public ResponseEntity<Void> writeReComment(
            @RequestBody @Valid WriteReCommentDto writeReCommentDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        Comment comment = new Comment(user, writeReCommentDto.getContent());
        beerService.addComment(writeReCommentDto.getBeerId(), comment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    //성능 최적화 전
//    @GetMapping("/api/comments/{beerId}")
//    public WrapperClass showComments(
//            @PathVariable("beerId") Long beerId,
//            @AuthenticationPrincipal PrincipalDetails principalDetails){
//        Beer beer = beerService.findOne(beerId);
//        List<Comment> comments = beer.getComments();
//        User user = principalDetails.getUser();
//        List<CommentDto> commentDtos = comments.stream()
//                .map(c -> new CommentDto(user, c))
//                .collect(Collectors.toList());
//        return new WrapperClass(commentDtos);
//    }

    @GetMapping("/api/comments/{beerId}")
    public WrapperClass showComments(
            @PathVariable("beerId") Long beerId){
        //fetch join 사용, comment와 user를 한번에 조회
        List<Comment> comments = commentService.findAllWithUser(beerId);
        List<CommentDto> commentDtos = comments.stream()
                .map(c -> new CommentDto(c.getUser(), c))
                .collect(Collectors.toList());
        return new WrapperClass(commentDtos);
    }

    @GetMapping("/api/recomment/{commentId}")  //drop down 눌렀을 경우
    public WrapperClass showReComments(
            @PathVariable("commentId") Long parentCommentId){
        List<Comment> recomments = commentService.findAllRecomments(parentCommentId);
        List<ReCommentDto> reCommentDtos = recomments.stream()
                .map(r -> new ReCommentDto(r.getUser(), r))
                .collect(Collectors.toList());
        return new WrapperClass(reCommentDtos);
    }
}
