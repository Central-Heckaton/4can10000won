package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.CommentDto;
import team_project.beer_community.dto.WriteCommentDto;
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
    @PostMapping("/api/comments/write-comment/{beerId}")
    public ResponseEntity<Void> writeComment(
            @PathVariable("beerId") Long beerId,
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @RequestBody @Valid WriteCommentDto writeCommentDto) {
        User user = principalDetails.getUser();
        Comment comment = new Comment(user, writeCommentDto.getContent(), writeCommentDto.getPoint());
        beerService.addComment(beerId, comment); //이 안에서 beer-comment/user-comment 모두 매핑
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
}
