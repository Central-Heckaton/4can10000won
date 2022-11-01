package team_project.beer_community.dto;

import lombok.Data;

@Data
public class CreateReportedCommentDto {
    private Long commentId;
    private String reportType;

    public CreateReportedCommentDto(Long commentId, String reportType) {
        this.commentId = commentId;
        this.reportType = reportType;
    }
}
