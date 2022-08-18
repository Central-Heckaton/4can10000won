package team_project.beer_community.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import team_project.beer_community.config.oauth.PrincipalOauth2UserService;
// 소셜로그인 후처리 절치
// 1. 코드받기(인증) 2.AccessToken(권한) 3. 사용자프로필 정보가져옴 4-1.그 정보를 토대로 회원가입을 자동으로 진행시키기도 함
// 4-2. (이메일, 전화번호,이름, 아이디)만 받았는대 -> 추가정보로(집주소, 닉네임)등등이 필요하다면 별도의 회원가입창을 띄워서 추가정보 입력받아야됨o

@Configuration // IoC 빈(Bean)을 등록
@EnableWebSecurity // Spring Security 필터가 Spring 필터체인에 등록됨(필터체인 관리 시작 어노테이션)
//  지금부터 등록할 필터가 기본 필터에 등록이 된다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
// @Secured 어노테이션 활성화(controller에서 확인가능), @PreAuthorize 과 @PostAuthorize어노테이션 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //소셜 로그인 이후 가져온 사용자의 정보를 기반으로 가입 및 정보 수정, 세션 저장 등의 추가적인 기능 수행
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/api/login")
                .usernameParameter("email") // ** username이 아니라 email으로 param을 받음 **
                .loginProcessingUrl("/api/login") // login주소가 호출되면 Spring Security가 낚아채서 대신 로그인 진행
                .failureForwardUrl("/api/login-handle")
                .defaultSuccessUrl("http://localhost:3000/search")
//                .defaultSuccessUrl("http://54.167.58.203:8080/search")
                .and()
                .oauth2Login()
                .loginPage("/api/login")
                .defaultSuccessUrl("http://localhost:3000/search")
//                .defaultSuccessUrl("http://54.167.58.203:8080/search")
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 구글소셜로그인 성공 후 코드를 받는게 아니라 AccessToken+사용자프로필정보 바로 함께받는다(편리함)
    }
}