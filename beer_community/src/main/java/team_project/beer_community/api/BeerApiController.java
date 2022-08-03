package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.dto.BeerDetailDto;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.service.BeerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BeerApiController {
    private final BeerService beerService;

    @GetMapping("/beers")
    public WrapperClass showAllBeers(){
        List<Beer> beers = beerService.findBeers();
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos);
    }

    @GetMapping("/beers/beer_detail")
    public WrapperClass beerDetail(){
        List<Beer> beers = beerService.findBeers();
        List<BeerDetailDto> beerDetailDtos = beers.stream()
                .map(b -> new BeerDetailDto(b, beerService.findAllTaste(b.getId())))
                .collect(Collectors.toList());
        return new WrapperClass(beerDetailDtos);
    }
}
