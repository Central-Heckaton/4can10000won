package team_project.beer_community.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WriteCommentDto {
    @NotNull
    private String content;
    @NotNull
    private double point;

    public WriteCommentDto(String content, double point) {
        this.content = content;
        this.point = point;
    }
}
