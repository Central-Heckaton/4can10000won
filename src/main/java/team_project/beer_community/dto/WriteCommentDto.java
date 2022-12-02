package team_project.beer_community.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class WriteCommentDto {
    @NotNull
    private String content;
    @NotNull
    private int point;

    @NotNull
    private Long beerId;

    public WriteCommentDto(String content, int point, Long beerId) {
        this.content = content;
        this.point = point;
        this.beerId = beerId;
    }
}
