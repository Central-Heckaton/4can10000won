package team_project.beer_community.dto;

import team_project.beer_community.domain.Comment;
import team_project.beer_community.domain.User;

public class ReCommentDto {
    private String username;
    private String userImageUrl;
    private String createdDate;
    private String content;

    public ReCommentDto(User user, Comment comment) {
        this.username = user.getUsername();
        this.userImageUrl = user.getImageUrl();
        this.createdDate = comment.getCreatedDate().toString();
        this.content = comment.getContent();
    }
}
