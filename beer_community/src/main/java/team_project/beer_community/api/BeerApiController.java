package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;

import team_project.beer_community.domain.BEER_TYPE;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDetailDto;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.dto.BeerRecDto;
import team_project.beer_community.dto.LikeBeerDto;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.LikeBeerService;
import team_project.beer_community.service.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class BeerApiController {
    private final BeerService beerService;
    private final UserService userService;
    private final LikeBeerService likeBeerService;

    @GetMapping("/api/search")
    public WrapperClass search(){
        System.out.println("========api search called =======");
        System.out.println("BeerApiController.search");
        List<Beer> beers = beerService.findBeers();
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        System.out.println("/api/search/beerDtos = " + beerDtos);
        return new WrapperClass(beerDtos);
    }

    //데이터를 수정하는게 아니므로 getmapping으로 바꿔야 하지 않나..?
    @PostMapping("/api/filter")
    public WrapperClass filter(@RequestBody HashMap<String, List<String>> beerTypeListData){
        System.out.println("========api filter called =======");
        List<String> beerTypeList = beerTypeListData.get("beerTypeList");
        List<Beer> finalBeerList = new ArrayList<>();
        List<Beer> beerList = beerService.findBeers();
        List<BEER_TYPE> beerTypes = new ArrayList<>(); // 맥주 타입들만 뽑아서 정리

        if(beerTypeList.size() == 0) {
            List<BeerDto> beerDtos = beerList.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
            return new WrapperClass(beerDtos);
        }
        for (Beer beer : beerList) {
            for (String beerType : beerTypeList) {
                if(beer.getBeerType().toString().equals(beerType)){
                    finalBeerList.add(beer); break;
                }
            }
        }
        List<BeerDto> beerDtos = finalBeerList.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos);
    }

    @GetMapping("/api/search/detail/{id}")
    public WrapperClass beerDetail(
            @PathVariable("id") Long beerId,
            @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        Beer beer = beerService.findOne(beerId);
        User user = principalDetails.getUser();
        List<LikeBeer> likeBeers = userService.findLikeBeers(user.getId());
        BeerDetailDto beerDetailDto = null;
        int count = 0;
        try {
            count = beer.getComments().size();
        } catch (Exception exception){
            System.out.println("BeerApiController.beerDetail/err: " + exception);
        }

        for (LikeBeer likeBeer : likeBeers) {
            if(likeBeer.getBeer() == beer){
                beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), count, Boolean.TRUE);
                return new WrapperClass(beerDetailDto);
            }
        }
        beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), count,Boolean.FALSE);
        return new WrapperClass(beerDetailDto);
    }

    //LikeBeer table을 수정하므로 PostMapping 사용
    @PostMapping("/api/like-beer")
    public ResponseEntity<Void> changeLikeState(
            @RequestBody LikeBeerDto likeBeerDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        Boolean state = likeBeerDto.getState();
        Long beerId = likeBeerDto.getBeerId();
        if(state){ // state == true
            Beer beer = beerService.findOne(beerId);
            List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(user.getId()); //fetch join 적용
            for (LikeBeer likeBeer : likeBeers) {
                if(likeBeer.getBeer().getId() == beerId){ //이미 좋아요 해놓은 맥주를 다시 좋아요 하는 경우
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
            LikeBeer likeBeer = new LikeBeer(beer);
            likeBeerService.join(likeBeer);
            Hibernate.initialize(user.getLikeBeers());
            user.addLikeBeer(likeBeer);
            beer.addLikeBeer(likeBeer);
        }
        else{ // state == false
            List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(); //fetch join 적용
            int flag = 0;
            for (LikeBeer likeBeer : likeBeers) {
                if(likeBeer.getBeer().getId() == beerId) {
                    userService.deleteLikeBeer(user.getId(), beerId);
                    flag = 1;
                    break;
                }
            }
            //좋아요 하지 않은 맥주를 좋아요 취소할 때
            if(flag == 0) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/api/recommend")
    public WrapperClass beerRec(){
        List<Beer> beers = beerService.findBeers();
        Random random = new Random();
        int value = random.nextInt(beers.size());
        Beer beer = beers.get(value);
        BeerRecDto beerRecDto = new BeerRecDto(beer, beerService.findAllTaste(beer.getId()));
        return new WrapperClass(beerRecDto);
    }

    @GetMapping("/api/beername-search/{beerName}") // @RequestParam으로 <input/>의 name을 써주자
    public WrapperClass beerSearch(@PathVariable("beerName") String beerName){
        System.out.println("BeerApiController.beerSearch");
        List<BeerDto> beerDtoList = beerService.findBeersWithBeerName(beerName);
        return new WrapperClass(beerDtoList);
    }
}
