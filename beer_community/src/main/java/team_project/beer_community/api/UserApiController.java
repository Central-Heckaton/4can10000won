package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.Role;
import team_project.beer_community.domain.User;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.repository.BeerRepository;
import team_project.beer_community.repository.UserRepository;
import team_project.beer_community.service.LikeBeerService;
import team_project.beer_community.service.UserService;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserApiController {
    private final LikeBeerService likeBeerService;
    private final UserRepository userRepository; // 사용자 정보 저장하기위해 사용
    private final UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화할 때 사용

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


    // localhost:8080/
    // localhost:8080
    @GetMapping({"", "/"})
    public String index(){
        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정: templates/ ~.mustache 를 읽는대
        // .html로 바꿔보겠음
        return "index";
    }

    // OAuth로그인을 해도 PrincipalDetails로 받을 수 있고
    // 일반로그인을 해도 PrincipalDetails로 받을 수 있다.
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        System.out.println("principalDetails = " + principalDetails); // 소셜로그인 or 일반로그인을 해도 출력됨.
        // 일반로그인시: principalDetails = PrincipalDetails(user=User(id=3, username=ko2), attributes=null)
        // 소셜로그인시: principalDetails = PrincipalDetails(user=User(id=4, username=고경환), attributes={sub=110000687855487904168, name=고경환, given_name=경환, family_name=고, picture=https://lh3.googleusercontent.com/a/AItbvmn09O3fy0FHLytziFKv3a3iNGsTAnjy0AiPuDaX=s96-c, email={이메일}, email_verified=true, locale=ko})
        return "user";
    }

    // Spring Security 가 낚아채버림
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/join")
    public String join_get(){
        return "join";
    }
    @PostMapping("/join")
    public String join_post(User user){
        System.out.println(user);
        System.out.println(user.getUsername()); // Entity에서 @Data로 getter/setter생성했기 때문에 가능
        User userEntity = userRepository.findByEmail(user.getEmail());
        // 동일한 이메일로 회원가입할 수 없도록 막는 로직
        if(userEntity != null){
            System.out.println("\n** IndexController.join_post/이미 동일한 이메일이 존재합니다! 다른이메일로 회원가입 해주시기 바랍니다 **");
            return "redirect:/join";
        }
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); // 일반적인 String 타입의 password는 Security를 통한 회원가입이 되지 않기 때문에 암호화 필요함o
        user.setRole(Role.ROLE_USER);
        userService.join(user);
//        userRepository.save(user);
        return "redirect:/login"; //  "/login" url로 redirect 시킴
    }

    @GetMapping("/admin")
    public @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/loginProc")
    public @ResponseBody String loginProc(){
        return "회원가입 완료되었습니다";
    }

    @Secured("ROLE_ADMIN") // ROLE_ADMIN만 '/info'로 접근가능하다
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')") // data함수 실행되기 전에 실행됨
    // @PostAuthorize("hasRole('ROLE_ADMIN')") 하게되면
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터";
    }

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication , @AuthenticationPrincipal PrincipalDetails userDetails){// DI(의존성주입)
        // @AuthenticationPrincipal이라는 어노테이션을 통해서 세션정보를 받을 수 있다.
        System.out.println("=====IndexController.testLogin====");
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        System.out.println("principalDetails.getUser() = " + principalDetails.getUser());
        // principalDetails.getUser() = User(id=1, username=ko1) -> User Entity에서 toString을 ({"id", "username"})만 했기 때문임.
        System.out.println("userDetails.getUser() = " + userDetails.getUser());
        // userDetails.getUser() = User(id=1, username=ko1) -> 위와 동일하기 때문에 id, username만 나옴
        return "세션 정보 확인하기";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication ,@AuthenticationPrincipal OAuth2User oauth){// DI(의존성주입)
        // @AuthenticationPrincipal이라는 어노테이션을 통해서 세션정보를 받을 수 있다.
        System.out.println("=====IndexController.testLogin====");
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        // oAuth2User.getAttributes() = {sub=103489475512635244738, name=‍고경환[재학 / 정보통신공학과], given_name=고경환[재학 / 정보통신공학과], family_name=‍, profile=https://plus.google.com/103489475512635244738, picture=https://lh3.googleusercontent.com/a/AItbvmlIUxyycyZvUHNNhzX20-5mvGrmrDbw6G1_Ylqn=s96-c, email=gkw1207@hufs.ac.kr, email_verified=true, locale=ko, hd=hufs.ac.kr}
        System.out.println("oauth.getAttributes() = " + oauth.getAttributes());
        // oauth.getAttributes() = {sub=103489475512635244738, name=‍고경환[재학 / 정보통신공학과], given_name=고경환[재학 / 정보통신공학과], family_name=‍, profile=https://plus.google.com/103489475512635244738, picture=https://lh3.googleusercontent.com/a/AItbvmlIUxyycyZvUHNNhzX20-5mvGrmrDbw6G1_Ylqn=s96-c, email=gkw1207@hufs.ac.kr, email_verified=true, locale=ko, hd=hufs.ac.kr}
        return "세션 정보 확인하기";
    }
    @GetMapping("/api/hello")
    public String hello(){
        return "hello!!";
    }
    //    @PostMapping("/api/login")
//    public String apiLogin(){
//        return "hello!!";
//    }
    @PostMapping("/api/join")
    public ResponseEntity<Void> apiJoin_post(User user) throws URISyntaxException {
        System.out.println(user);
        System.out.println(user.getUsername()); // Entity에서 @Data로 getter/setter생성했기 때문에 가능
        // 동일한 이메일로 회원가입할 수 없도록 막는 로직
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); // 일반적인 String 타입의 password는 Security를 통한 회원가입이 되지 않기 때문에 암호화 필요함o
        user.setRole(Role.ROLE_USER);
        userService.join(user);
//        userRepository.save(user);
        System.out.println("IndexController.apiJoin_post/Success Join User : " + user);
        URI redirectUri = new URI("http://localhost:3000/");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirectUri);
        System.out.println("HttpHeaders = " + headers);
        return new ResponseEntity<>(headers, HttpStatus.CREATED); // 201
    }
    @GetMapping("/api/join")
    public List<String> apiJoin_get(){
        List<User> userList = userRepository.findAll();
        System.out.println("userList = " + userList);
        List<String> list = new ArrayList<>();
        System.out.println("Before list = " + list); // []
        for (User user1 : userList) {
            System.out.println("user1 = " + user1);
            list.add(user1.getEmail());
        }
        // 동일한 이메일로 회원가입할 수 없도록 막는 로직
        System.out.println("After list = " + list); // [ko1@naver.com, ko1@naver.com, ko2@naver.com]
        return list;

    }
    @GetMapping("/test/join")
    public List<String> testJoin_get(){
        List<User> userList = userRepository.findAll();
        System.out.println("userList = " + userList);
        List<String> list = new ArrayList<>();
        System.out.println("Before list = " + list); // []
        for (User user1 : userList) {
            System.out.println("user1 = " + user1);
            list.add(user1.getEmail());
        }
        // 동일한 이메일로 회원가입할 수 없도록 막는 로직
        System.out.println("After list = " + list); // [ko1@naver.com, ko1@naver.com, ko2@naver.com]
        return list;

    }
    @GetMapping("/api/user")
    public Map<String, Object> apiUser(@AuthenticationPrincipal PrincipalDetails principalDetails){
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
    @GetMapping("/api/beer/{id}")
    public Beer detailBeer(@PathVariable Long id){
//        Beer beer = beerRepository.findById(id);
        Beer beer = null;
        return beer;
    }
}
