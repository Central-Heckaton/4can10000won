package team_project.beer_community.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class WriteCommentDto {
    @NotNull
    private String content;
    @NotNull
    private double point;

    @NotNull
    private Long beerId;

    public WriteCommentDto(String content, double point, Long beerId) {
        this.content = content;
        this.point = point;
        this.beerId = beerId;
    }
}
