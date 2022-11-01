package team_project.beer_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.REPORT_TYPE;
import team_project.beer_community.domain.ReportedComment;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.ReportedCommentRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportedCommentService {
    private final ReportedCommentRepository reportedCommentRepository;
    private final CommentService commentService;

    public Page<ReportedComment> findAllReportedComments(Pageable pageable){
        return reportedCommentRepository.findAll(pageable);
    }

    public ReportedComment findOne(Long reportedCommentId){
        return reportedCommentRepository.findById(reportedCommentId).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void addReportedComment(Long commentId, User user, REPORT_TYPE type) {
        Comment findComment = commentService.findOne(commentId);
        ReportedComment reportedComment = new ReportedComment(user, findComment, type);
        reportedCommentRepository.save(reportedComment);
        System.out.println("reportedComment.getId() = " + reportedComment.getId());
        user.addReportedComment(reportedComment);
        findComment.addReportedComment(reportedComment);
    }

    @Transactional
    public Long delete(ReportedComment reportedComment){
        reportedCommentRepository.delete(reportedComment);
        return reportedComment.getId();
    }
}
