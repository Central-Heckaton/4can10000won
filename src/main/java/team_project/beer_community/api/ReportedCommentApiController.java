package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.REPORT_TYPE;
import team_project.beer_community.domain.ReportedComment;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.CreateReportedCommentDto;
import team_project.beer_community.dto.ReportedCommentDto;
import team_project.beer_community.service.CommentService;
import team_project.beer_community.service.ReportedCommentService;
import team_project.beer_community.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportedCommentApiController {

    private final ReportedCommentService reportedCommentService;
    private final UserService userService;
    private final CommentApiController commentApiController;

    @GetMapping("/api/reported-comments") // /api/reported-comments?0 형태로 요청
    public ResponseEntity showAllReportedComments(@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        System.out.println("ReportedCommentApiController.showAllReportedComments");
         List<ReportedCommentDto> reportedCommentDtos = reportedCommentService.findAllReportedComments(pageable)
                 .map(reportedComment -> new ReportedCommentDto(reportedComment, reportedComment.getUser(), reportedComment.getComment())).getContent();
         return ResponseEntity.status(HttpStatus.OK).body(new WrapperClass<>(reportedCommentDtos));
    }

    @PostMapping("/api/reported-comments")
    public ResponseEntity reportComment(@RequestBody CreateReportedCommentDto createReportedCommentDto,
                                        @AuthenticationPrincipal PrincipalDetails principalDetails){
        REPORT_TYPE type = REPORT_TYPE.valueOf(createReportedCommentDto.getReportType());
        reportedCommentService.addReportedComment(createReportedCommentDto.getCommentId(), principalDetails.getUser(), type);
        return new ResponseEntity(HttpStatus.OK); //body를 비워둘 때는 이와 같이 사용
    }

    @PostMapping("/api/reported-comments-test/create2")
    public ResponseEntity reportCommentTest(@RequestBody CreateReportedCommentDto createReportedCommentDto){
        REPORT_TYPE type = REPORT_TYPE.valueOf(createReportedCommentDto.getReportType());
        User reporter = userService.findOne(1L);
        System.out.println("createReportedCommentDto = " + createReportedCommentDto);
        reportedCommentService.addReportedComment(createReportedCommentDto.getCommentId(), reporter, type);
        return new ResponseEntity(HttpStatus.OK); //body를 비워둘 때는 이와 같이 사용
    }

    @GetMapping("/api/reported-comments/reject/{reportedCommentId}") //신고 거절
    public ResponseEntity rejectReportedComment(@PathVariable("reportedCommentId") Long reportedCommentId){
        try{
            ReportedComment reportedComment = reportedCommentService.findOne(reportedCommentId);
            reportedCommentService.delete(reportedComment);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/reported-comments/delete/{reportedCommentId}") //신고된 댓글 삭제
    public ResponseEntity deleteReportedComment(@PathVariable("reportedCommentId") Long reportedCommentId){
        try{
            ReportedComment reportedComment = reportedCommentService.findOne(reportedCommentId);
            Comment findComment = reportedComment.getComment();
            reportedCommentService.delete(reportedComment);
            commentApiController.deleteComment(findComment.getId()); //부모 댓글일 경우 대댓글 또한 삭제
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }catch(Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
