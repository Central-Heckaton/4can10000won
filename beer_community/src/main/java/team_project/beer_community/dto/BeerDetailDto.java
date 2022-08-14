package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.Taste;

import java.util.List;

@Data
public class BeerDetailDto {
    private Long id;
    private String imageUrl;
    private String beerName;
    private double totalPoint;
    private String information;
    private double alcoholDegree;
    private List<Taste> tastes;

    private Boolean is_liked; // 찜하기 상태 on/off ->  true/false

    private int count; // 전체 리뷰수

    public BeerDetailDto(Beer beer, List<Taste> tastes, int count, Boolean is_liked) {
        this.id = beer.getId();
        this.imageUrl = beer.getImageUrl();
        this.beerName = beer.getBeerName();
        this.totalPoint = beer.getTotalPoint();
        this.information = beer.getInformation();
        this.alcoholDegree = beer.getAlcoholDegree();
        this.tastes = tastes;
        this.count = count;
        this.is_liked = is_liked;
    }
}


