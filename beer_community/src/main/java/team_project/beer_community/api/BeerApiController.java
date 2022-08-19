package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;

import team_project.beer_community.domain.*;
import team_project.beer_community.dto.BeerDetailDto;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.dto.BeerRecDto;
import team_project.beer_community.dto.LikeBeerDto;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping("/api/search")
    public WrapperClass search(){
        System.out.println("========api search called =======");
        System.out.println("BeerApiController.search");
        List<Beer> beers = beerService.findBeers();
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        System.out.println("/api/search/beerDtos = " + beerDtos);
        return new WrapperClass(beerDtos);
    }

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
        int parentCount = commentService.findParentCountWithBeerId(beerId);
        for (LikeBeer likeBeer : likeBeers) {
            if(likeBeer.getBeer() == beer){
                beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), parentCount, Boolean.TRUE);
                return new WrapperClass(beerDetailDto);
            }
        }
        beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), parentCount,Boolean.FALSE);
        return new WrapperClass(beerDetailDto);
    }

    @GetMapping("/api/search/detail/{beerId}/{userId}")
    public WrapperClass beerDetail_test(
            @PathVariable("beerId") Long beerId,
            @PathVariable("userId") Long userId)
    {
        Beer beer = beerService.findOne(beerId);
        User user = userService.findOne(userId);
        List<LikeBeer> likeBeers = userService.findLikeBeers(user.getId());
        BeerDetailDto beerDetailDto = null;
        int parentCount = commentService.findParentCountWithBeerId(beerId);
        for (LikeBeer likeBeer : likeBeers) {
            if(likeBeer.getBeer() == beer){
                beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), parentCount, Boolean.TRUE);
                return new WrapperClass(beerDetailDto);
            }
        }
        beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(beerId), parentCount,Boolean.FALSE);
        return new WrapperClass(beerDetailDto);
    }

    //LikeBeer table을 수정하므로 PostMapping 사용
    @PostMapping("/api/like-beer")
    public ResponseEntity<Void> changeLikeState(
            @RequestBody LikeBeerDto likeBeerDto,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = userService.getUserWithInitializedLikeBeers(principalDetails.getUser().getId());
        Boolean state = likeBeerDto.getState();
        Long beerId = likeBeerDto.getBeerId();
        if(state){ // state == true
            Beer beer = beerService.findOne(beerId);
            LikeBeer likeBeer = new LikeBeer(beer);
            user.addLikeBeer(likeBeer);
            beer.addLikeBeer(likeBeer);
            likeBeerService.join(likeBeer);
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

    @GetMapping("/api/beername-search/{beerName}") // @RequestParam으로 <input/>의 name을 써주자
    public WrapperClass beerSearch(@PathVariable("beerName") String beerName){
        System.out.println("BeerApiController.beerSearch");
        List<BeerDto> beerDtoList = beerService.findBeersWithBeerName(beerName);
        return new WrapperClass(beerDtoList);
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
}
