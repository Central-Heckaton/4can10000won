package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Beer;

@Data
public class BeerDto {
    private Long id;
    private String imageUrl;
    private String beerName;
    private double totalPoint;
    private String information;

    public BeerDto(Beer beer) {
        this.id = beer.getId();
        this.imageUrl = beer.getImageUrl();
        this.beerName = beer.getBeerName();
        this.totalPoint = beer.getTotalPoint();
        this.information = beer.getInformation();
    }
}
