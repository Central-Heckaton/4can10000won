package team_project.beer_community.dto;

import lombok.Data;

@Data
public class LikeBeerDto {
    private Long beerId;
    private Boolean state;  //api에서 바꿀 상태
}
