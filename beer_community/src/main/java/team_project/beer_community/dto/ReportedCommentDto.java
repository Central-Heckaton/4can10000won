package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.REPORT_TYPE;
import team_project.beer_community.domain.ReportedComment;
import team_project.beer_community.domain.User;

import java.time.format.DateTimeFormatter;

@Data

public class ReportedCommentDto {
    private Long id;
    private String reporterName;
    private String content;
    private REPORT_TYPE reportType;
    private String createdDate;

    public ReportedCommentDto(ReportedComment reportedComment, User user, Comment comment) {
        this.id = reportedComment.getId();
        this.reporterName = user.getUsername();
        this.content = comment.getContent();
        this.reportType = reportedComment.getReportType();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH시 mm분 ss초");
        this.createdDate = reportedComment.getCreatedDate().format(dtf);
    }
}
