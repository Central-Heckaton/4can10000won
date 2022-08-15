package team_project.beer_community.api;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.domain.*;
import team_project.beer_community.dto.BeerDto;
import team_project.beer_community.dto.EmailDto;
import team_project.beer_community.dto.UserInfoDto;
import team_project.beer_community.dto.UserJoinDto;
import team_project.beer_community.repository.BeerRepository;
import team_project.beer_community.repository.UserRepository;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.LikeBeerService;
import team_project.beer_community.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
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
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder; // password 암호화할 때 사용

    @GetMapping("/api/likebeers")
    public WrapperClass showLikeBeers(@AuthenticationPrincipal PrincipalDetails principalDetails){
        //fetch join 사용
        User user = principalDetails.getUser();
        List<LikeBeer> likeBeers = likeBeerService.findAllWithBeer(user.getId());
        List<Beer> beers = new ArrayList<>();
        for (LikeBeer likeBeer : likeBeers) {
            beers.add(likeBeer.getBeer());
            System.out.println("likeBeer.getBeer() = " + likeBeer.getBeer());
        }
        List<BeerDto> beerDtos = beers.stream().map(b -> new BeerDto(b)).collect(Collectors.toList());
        return new WrapperClass(beerDtos); //api의 확장이 가능하도록 wrapper 클래스로 감싸서 리스트를 return
    }

    @PostMapping("/api/check-email-duplicate")
    public ResponseEntity<Map<String, String>> checkEmailDuplicate(@Valid @RequestBody EmailDto emailDto, Errors errors){
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> httpBody = new HashMap<>();
        if (errors.hasErrors()){
            Map<String, String> validatorResult = userService.validateHandling(errors);
            String errorMessage = "";
            for (String key : validatorResult.keySet()) {
                errorMessage = validatorResult.get(key);
            }
            httpBody.put("error", errorMessage);
            return new ResponseEntity<Map<String, String>>(httpBody, headers, HttpStatus.BAD_REQUEST); // 400
        }
        try{
            String email = emailDto.getEmail();
            List<User> userList = userService.findUsers();
            HttpStatus status = HttpStatus.ACCEPTED; // 202
            for (User user : userList) {
                if(user.getEmail().equals(email)){
                    status = HttpStatus.CONFLICT; // 409
                    break;
                }
            }
            return new ResponseEntity<>(httpBody, headers, status);
        } catch (Exception exception){
            System.out.println("apiLogin/exception = " + exception);
            return new ResponseEntity<>(httpBody, headers, HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/api/join")
    public ResponseEntity<Map<String, String>> join(@RequestBody @Valid UserJoinDto userJoinDto, Errors errors) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        Map<String, String> httpBody = new HashMap<>();
        if (errors.hasErrors()){
            Map<String, String> validatorResult = userService.validateHandling(errors);
            String errorMessage = "";
            for (String key : validatorResult.keySet()) {
                errorMessage = validatorResult.get(key);
            }
            httpBody.put("error", errorMessage);
            return new ResponseEntity<Map<String, String>>(httpBody, headers, HttpStatus.BAD_REQUEST); // 400
        }
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
            userService.join(user);
            return new ResponseEntity<>(null, headers, HttpStatus.CREATED); // 201
        } catch (Exception exception) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST); // 400
        }
    }

    @GetMapping("/api/user")
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

    @PostMapping("/api/update-user-info")// {"username": username, "image_url": image_url}
    public ResponseEntity<Void> updateUserInfo(@AuthenticationPrincipal PrincipalDetails principalDetails, @RequestBody UserInfoDto userInfoDto){
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        try{
            User principalDetailsUser = principalDetails.getUser();
            userService.updateName(principalDetailsUser.getId(), userInfoDto.getUsername()); // DB에 사용자이름 변경사항 반영
            principalDetailsUser.setUsername(userInfoDto.getUsername()); // 세션에도 변경사항 반영해줘야됨
            status = HttpStatus.ACCEPTED;

        } catch (Exception exception) {
            System.out.println("updateUserInfo/exception = " + exception);
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(null, headers, status);
    }

    @GetMapping("/api/test")
    public String test(){
        return "test good";
    }

    @PostMapping("/api/login-handle")
    public void loginHandle(HttpServletResponse response) throws IOException {
        System.out.println("loginHandle");
        HttpHeaders headers = new HttpHeaders();
        String redirect_uri = "http://localhost:3000/login-retry"; // 로그인 재시도
        response.addHeader("login_result", "fail");
        response.sendRedirect(redirect_uri);
    }
}
