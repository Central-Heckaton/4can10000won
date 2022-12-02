package team_project.beer_community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
@Data
@NoArgsConstructor
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
