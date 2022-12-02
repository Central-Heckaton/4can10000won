package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;

@Data
public class CommentDto {
    private Long id;
    private Long writerId;
    private String username;
    private String userImageUrl;
    private String createdDate;
    private String content;
    private double point;

    private int reCount;

    public CommentDto(User user, Comment comment, int reCount) {
        this.id = comment.getId();
        this.writerId = user.getId();
        this.username = user.getUsername();
        this.userImageUrl = user.getImageUrl();
        this.createdDate = comment.getCreatedDate().toString();
        this.content = comment.getContent();
        this.point = comment.getPoint();
        this.reCount = reCount;
    }
}
