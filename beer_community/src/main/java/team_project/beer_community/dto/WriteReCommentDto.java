package team_project.beer_community.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
@Data
public class WriteReCommentDto {
    @NotNull
    private String content;

    @NotNull
    private Long parentId;

    private Long beerId;

    public WriteReCommentDto(String content, Long parentId) {
        this.content = content;
        this.parentId = parentId;
    }
}
