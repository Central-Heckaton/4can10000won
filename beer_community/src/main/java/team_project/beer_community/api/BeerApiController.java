package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;

import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;

import team_project.beer_community.domain.BEER_TYPE;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDetailDto;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.dto.BeerRecDto;
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
        List<Beer> beers = beerService.findBeers();
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos);
    }

    @PostMapping("/api/filter")
    public WrapperClass filter(@RequestBody HashMap<String, List<String>> beerTypeListData){
        List<String> beerTypeList = beerTypeListData.get("beerTypeList");
        System.out.println("beerTypeList = " + beerTypeList);
        List<Beer> finalBeerList = new ArrayList<>();
        List<Beer> beerList = beerService.findBeers();
        List<BEER_TYPE> beerTypes = new ArrayList<>(); // 맥주 타입들만 뽑아서 정리
        if(beerList.size() >= 1) {System.out.println("beerList[0].getBeerType = " + beerList.get(0).getBeerType().toString());}
        if(beerTypeList.size() >= 1) {System.out.println("beerTypeList[0].getBeerType = " + beerTypeList.get(0));}
        System.out.println("beerTypeList[0].equals(beerList[0].getBeerType) = " + beerTypeList.get(0).equals(beerList.get(0).getBeerType().toString()));
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
            @PathVariable("id") Long id,
            @AuthenticationPrincipal PrincipalDetails principalDetails)
    {
        Beer beer = beerService.findOne(id);
        User user = principalDetails.getUser();
        List<LikeBeer> likeBeers = userService.findLikeBeers(user.getId());
        BeerDetailDto beerDetailDto = null;
        for (LikeBeer likeBeer : likeBeers) {
            if(likeBeer.getBeer() == beer){
                beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(id), Boolean.TRUE);
                return new WrapperClass(beerDetailDto);
            }
        }
        beerDetailDto = new BeerDetailDto(beer, beerService.findAllTaste(id), Boolean.FALSE);
        return new WrapperClass(beerDetailDto);
    }

    @GetMapping("/api/search/detail/{userId}/{beerId}")
    public WrapperClass beerDetail_test(
            @PathVariable("userId") Long userId,
            @PathVariable("beerId") Long beerId)
    {
        Beer beer = beerService.findOne(beerId);
        List<LikeBeer> likeBeers = userService.findLikeBeers(userId);
        for (LikeBeer likeBeer : likeBeers) {
            if(likeBeer.getBeer() == beer){
                return new WrapperClass(new BeerDetailDto(beer, beerService.findAllTaste(beerId), Boolean.TRUE));
            }
        }
        return new WrapperClass(new BeerDetailDto(beer, beerService.findAllTaste(beerId), Boolean.FALSE));
    }

    @GetMapping("/api/beer-like/{beerid}/{state}")
    public ResponseEntity<Void> changeLikeState(
            @PathVariable("beerid") Long beerId,
            @PathVariable("state") int state,
            @AuthenticationPrincipal PrincipalDetails principalDetails){
        User user = principalDetails.getUser();
        if(state == 1){
            Beer beer = beerService.findOne(beerId);
            //이미 좋아요 해놓은 맥주를 다시 좋아요 하는 경우
            List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(user.getId()); //fetch join 적용
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
                    userService.deleteLikeBeer(user.getId(), beerId);
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
    public BeerRecDto beerRec(){
        List<Beer> beers = beerService.findBeers();
        Random random = new Random();
        int value = random.nextInt(beers.size());
        Beer beer = beers.get(value);
        BeerRecDto beerRecDto = new BeerRecDto(beer, beerService.findAllTaste(beer.getId()));
        return beerRecDto;
    }
}
