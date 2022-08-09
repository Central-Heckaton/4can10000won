package team_project.beer_community.config.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team_project.beer_community.config.auth.PrincipalDetails;
import team_project.beer_community.config.oauth.provider.GoogleUserInfo;
import team_project.beer_community.config.oauth.provider.NaverUserInfo;
import team_project.beer_community.config.oauth.provider.OAuth2UserInfo;
import team_project.beer_community.domain.Role;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // 함수종료시 @AuthenticationPrincipal 어노테이션이 만들어짐
    @Override // 구글소셜로그인 후 구글로 부터 받은 userRequest 데이터에 대한 후처리되는 함수
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);
        // org.springframwork.security.oauth2.client.userinfo.OAuth2UserRequest@4e6edb55
        System.out.println("getClientRegistration() = " + userRequest.getClientRegistration());
        // registrationId로 어떤 OAuth로 로그인 했는지 확인가능(ex. google, naver)
        System.out.println("getAccessToken = " + userRequest.getAccessToken().getTokenValue());
        // 구글로그인 버튼클릭 -> 구글로그인 창 -> 로그인을 완료 -> code를 return(OAuth-Client라이브러리) -> code를 사용해서 AccessToken을 요청해서 받는다.
        // 여기까지가 userRequest정보이다. -> loadUser() 함수호출 -> 구글로부터 회원프로필 얻을 수 있다.(ex. email, family_name 등등)


        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        // {sub=101301106118139334837, name=고경환, given_name=경환, family_name=고, picture=https://lh3.googleusercontent.com/a-/AFdZucqfqgcr-H-cRolGyJETVNk, email=gkw1207@likelion.org, email_verified=true, locale=en, hd=likelion.org}
        // **회원가입할때 저장될 정보** => username: "google_101301106118139334837", password: "암호화(get in there)", email: "gkw1207@likelion.org", role: "ROLE_USER"

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());

        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            // oAuth2User.getAttributes() = {resultcode=00, message=success, response={id=gemgDkrwZwCX-gA3TE7mHR0OAW4doONhq7ZUTonE1YU, nickname=고경환, profile_image=https://phinf.pstatic.net/contact/20220316_119/1647425542931YWABt_JPEG/image.jpg, email={이메일}, name=고경환, birthday=12-07, birthyear=1998}}
            // getAttributs()한것에서 key값이 response에 해당하는 value를 param으로 넘겨줘야한다.
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
            System.out.println("네이버 로그인 요청");

        }else{
            System.out.println("\n** 구글과 네이버만 소셜로그인이 가능합니다! **");
        }

        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId(); // sub키에 저장된 값은 google에서 사용자에게 부여한 pk이다
        String username = oAuth2UserInfo.getUsername();
        String password = bCryptPasswordEncoder.encode("password") ; // 소셜로그인이기 때문에 굳이 저장안해도되지만 임의로 생성해서 저장함
//        String password = "password";
        String email = oAuth2UserInfo.getEmail();
        Role role = Role.ROLE_USER;
        System.out.println("PrincipalOauth@UserService.java/username = " + username);
        System.out.println("PrincipalOauth@UserService.java/getAttributes() = " + oAuth2User.getAttributes());
//        User userEntity = userRepository.findByUsername(username); // **username이외의 필드로 중복성검사 체크 필요!**
        User userEntity = userRepository.findByEmail(email);
        System.out.println("userEntity = " + userEntity);
        if(userEntity == null){
            // User에 생성자를 통해 새로운 User를 생성시킴(회원가입)
            System.out.println("PrincipalOauth2UserService.loadUser/처음 로그인하는군요 회원가입 진행하겠습니다");
            userEntity = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .birthday(null)
                    .imageUrl(null)
                    .build();
            userRepository.save(userEntity);
        } else{
            System.out.println("PrincipalOauth2UserService.loadUser/회원가입이 이미 되어있습니다.");
        }
        // 회원가입이 이미 되어있다면 그냥 앞서받은 userEntity사용해도 됨
        return new PrincipalDetails(userEntity, oAuth2User.getAttributes()); // Authentication에 저장된다.
    }
}
