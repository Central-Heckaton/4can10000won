package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDetailDto;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.LikeBeerService;
import team_project.beer_community.service.UserService;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BeerApiController {
    private final BeerService beerService;
    private final UserService userService;
    private final LikeBeerService likeBeerService;

    @GetMapping("/api/beers")
    public WrapperClass showAllBeers(){
        List<Beer> beers = beerService.findBeers();
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos);
    }

    @GetMapping("/api/beers/{id}")
    public BeerDetailDto beerDetail(
            @PathVariable("id") Long id)
    {
        Beer beer = beerService.findOne(id);
        BeerDetailDto beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(id));
        return beerDetailDto;
    }

    @GetMapping("/api/beer-like/{userid}/{beerid}/{state}")
    public ResponseEntity<Void> changeLikeState(
            @PathVariable("userid") Long userId,
            @PathVariable("beerid") Long beerId,
            @PathVariable("state") int state){
        if(state == 1){
            User user = userService.findOne(userId);
            Beer beer = beerService.findOne(beerId);
            //이미 좋아요 해놓은 맥주를 다시 좋아요 하는 경우
            List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(userId); //fetch join 적용
            for (LikeBeer likeBeer : likeBeers) {
                if(likeBeer.getBeer().getId() == beerId){
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            LikeBeer likeBeer = new LikeBeer(beer);
            user.addLikeBeer(likeBeer);
            beer.addLikeBeer(likeBeer);
            likeBeerService.join(likeBeer);
        }
        else{
            List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(); //fetch join 적용
            int flag = 0;
            for (LikeBeer likeBeer : likeBeers) {
                if(likeBeer.getBeer().getId() == beerId) {
                    userService.deleteLikeBeer(userId, beerId);
                    flag = 1;
                    break;
                }
            }
            //좋아요 하지 않은 맥주를 좋아요 최소할 때
            if(flag == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/api/recommend")
    public BeerDetailDto beerRec(){
        List<Beer> beers = beerService.findBeers();
        Random random = new Random();
        int value = random.nextInt(beers.size());
        Beer beer = beers.get(value);
        BeerDetailDto beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beer.getId()));
        return beerDetailDto;
    }
}
