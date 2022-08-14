package team_project.beer_community.dto;

import lombok.Data;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.Taste;

import java.util.List;

@Data
public class BeerRecDto {
    private Long id;
    private String imageUrl;
    private String beerName;
    private double totalPoint;
    private String information;
    private double alcoholDegree;
    private List<Taste> tastes;

    public BeerRecDto(Beer beer, List<Taste> tastes) {
        this.id = beer.getId();
        this.imageUrl = beer.getImageUrl();
        this.beerName = beer.getBeerName();
        this.totalPoint = beer.getTotalPoint();
        this.information = beer.getInformation();
        this.alcoholDegree = beer.getAlcoholDegree();
        this.tastes = tastes;
    }
}
