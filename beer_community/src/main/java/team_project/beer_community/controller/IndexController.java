package team_project.beer_community.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import team_project.beer_community.api.WrapperClass;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.BEER_TYPE;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.Role;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.UserJoinDto;
import team_project.beer_community.repository.BeerRepository;
import team_project.beer_community.repository.UserRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Array;
import java.util.*;

//@Controller // View를 리턴하겠다
@RestController // json타입으로 주고받을 때 사용o
@RequestMapping("api")
public class IndexController {

    @Autowired
    private UserRepository userRepository; // 사용자 정보 저장하기위해 사용
    @Autowired
    private BeerRepository beerRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화할 때 사용


    public IndexController() {
    }


    @RequestMapping(method = RequestMethod.POST, path = "/check-email-duplicate")
    public ResponseEntity<Void> checkEmailDuplicate(@RequestBody HashMap<String, String> emailData){
        HttpHeaders headers = new HttpHeaders();
        try{
            System.out.println("IndexController.checkEmailDuplicate");
            String email = emailData.get("email");
            List<User> userList = userRepository.findAll();
            HttpStatus status = HttpStatus.ACCEPTED; // 202
            for (User user : userList) {
                if(user.getEmail().equals(email)){
                    status = HttpStatus.CONFLICT; // 409
                    break;
                }
            }
            return new ResponseEntity<>(null, headers, status);
        } catch (Exception exception){
            System.out.println("apiLogin/exception = " + exception);
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(method = RequestMethod.POST, path="/join")
    public ResponseEntity<Void> join(@RequestBody UserJoinDto userJoinDto) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        try{
            String rawPassword = userJoinDto.getPassword();
            String encPassword = bCryptPasswordEncoder.encode(rawPassword);
            userJoinDto.setPassword(encPassword); // 일반적인 String 타입의 password는 Security를 통한 회원가입이 되지 않기 때문에 암호화 필요함o
            User user = new User(
                    userJoinDto.getEmail(),
                    encPassword,
                    userJoinDto.getUsername(),
                    userJoinDto.getBirthday(),
                    userJoinDto.getImageUrl(),
                    Role.ROLE_USER);
            userRepository.save(user); // ** service계층을 통해 DB접근하도록 수정 해야됨 **
            return new ResponseEntity<>(null, headers, HttpStatus.CREATED); // 201
        } catch (Exception exception) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST); // 400
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user")
    public Map<String, Object> userInfo(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails = " + principalDetails); // 소셜로그인 or 일반로그인을 해도 출력됨.
        // 일반로그인시: principalDetails = PrincipalDetails(user=User(id=3, username=ko2), attributes=null)
        // 소셜로그인시: principalDetails = PrincipalDetails(user=User(id=4, username=고경환), attributes={sub=110000687855487904168, name=고경환, given_name=경환, family_name=고, picture=https://lh3.googleusercontent.com/a/AItbvmn09O3fy0FHLytziFKv3a3iNGsTAnjy0AiPuDaX=s96-c, email={이메일}, email_verified=true, locale=ko})
        User user = principalDetails.getUser();
        String username = user.getUsername();
        String email = user.getEmail();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("email", email);
        System.out.println("map = " + map); // map = {email=ko2@naver.com, username=ko2}
        return map;
    }

    @RequestMapping(method = RequestMethod.POST, path="/filter")
    public WrapperClass filter(@RequestBody HashMap<String, List<String>> beerTypeListData){
        HttpHeaders headers = new HttpHeaders();
        List<String> beerTypeList = beerTypeListData.get("beerTypeList");
        System.out.println("beerTypeList = " + beerTypeList);
        List<Beer> finalBeerList = new ArrayList<>();
        List<Beer> beerList = beerRepository.findAll();
        List< BEER_TYPE> beerTypes = new ArrayList<>(); // 맥주 타입들만 뽑아서 정리
        if(beerList.size() >= 1) {System.out.println("beerList[0].getBeerType = " + beerList.get(0).getBeerType().toString());}
        if(beerTypeList.size() >= 1) {System.out.println("beerTypeList[0].getBeerType = " + beerTypeList.get(0));}
        System.out.println("beerTypeList[0].equals(beerList[0].getBeerType) = " + beerTypeList.get(0).equals(beerList.get(0).getBeerType().toString()));
        if(beerTypeList.size() == 0) { return new WrapperClass(beerList); }
        for (Beer beer : beerList) {
            for (String beerType : beerTypeList) {
                if(beer.getBeerType().toString().equals(beerType)){
                    finalBeerList.add(beer); break;
                }
            }
        }
        return new WrapperClass(finalBeerList);
    }

    @RequestMapping(method = RequestMethod.GET, path="/search")
    public WrapperClass search(){
        HttpHeaders headers = new HttpHeaders();
        List<Beer> beerList = beerRepository.findAll();
        System.out.println("beerList = " + beerList);
        return new WrapperClass(beerList);
    }
}