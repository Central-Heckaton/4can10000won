package team_project.beer_community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;

@Controller // View를 리턴하겠다
//@RestController // json타입으로 주고받을 때 사용o
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/user")
    public @ResponseBody String user(){
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
}
