package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @GetMapping("/likebeers")
    public WrapperClass showLikeBeers(Long id){
        User findUser = userService.findOne(id);
        //배치 전략 사용
        List<LikeBeer> likeBeers = userService.findLikeBeers(findUser.getId());
        List<Beer> beers = new ArrayList<>();
        for (LikeBeer likeBeer : likeBeers) {
            beers.add(likeBeer.getBeer());  //beer만 불러오면 되기 때문에 별도의 fetch join이 필요 없다
        }
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos); //api의 확장이 가능하도록 wrapper 클래스로 감싸서 리스트를 return
    }

}
