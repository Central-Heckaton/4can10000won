package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;

@Data
public class CommentDto {
    private String username;
    private String userImageUrl;
    private String createdDate;
    private String content;
    private double point;

    public CommentDto(User user, Comment comment) {
        this.username = user.getUsername();
        this.userImageUrl = user.getImageUrl();
        this.createdDate = comment.getCreatedDate().toString();
        this.content = comment.getContent();
        this.point = comment.getPoint();
    }
}
