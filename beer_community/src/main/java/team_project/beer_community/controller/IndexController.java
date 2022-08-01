package team_project.beer_community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;

@Controller // View를 리턴하겠다
//@RestController // json타입으로 주고받을 때 사용o
public class IndexController {

    @Autowired
    private UserRepository userRepository; // 사용자 정보 저장하기위해 사용

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화할 때 사용


    public IndexController() {
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
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword); // 일반적인 String 타입의 password는 Security를 통한 회원가입이 되지 않기 때문에 암호화 필요함o
        user.setRole("ROLE_USER");
        userRepository.save(user);
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
    public @ResponseBody String testLogin(Authentication authentication ,@AuthenticationPrincipal PrincipalDetails userDetails){// DI(의존성주입)
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
}
