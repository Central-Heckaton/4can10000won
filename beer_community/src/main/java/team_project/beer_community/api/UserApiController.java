package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.repository.LikeBeerRepository;
import team_project.beer_community.service.LikeBeerService;
import team_project.beer_community.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final LikeBeerService likeBeerService;

    @GetMapping("/api/likebeers/{id}")
    public WrapperClass showLikeBeers(@PathVariable("id") Long userid){
        //fetch join 사용
        List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(userid);
        List<Beer> beers = new ArrayList<>();
        for (LikeBeer likeBeer : likeBeers) {
            beers.add(likeBeer.getBeer());
            System.out.println("likeBeer.getBeer() = " + likeBeer.getBeer());
        }
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos); //api의 확장이 가능하도록 wrapper 클래스로 감싸서 리스트를 return
    }
}