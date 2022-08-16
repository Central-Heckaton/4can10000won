package team_project.beer_community.config.auth;


// Securitnull/login" 주소로 요청이 오면 낚아채서 로그인을 진행시킨다
// 로그인을 진행이 완료가 되면 Security session을 만들어준다(Security ContextHolder라는 키값에 넣어준다)
// security가 만드는 session에 들어갈 수 있는 Object가 정해져있다.
//  => Authentication 타입의 객체(안에 User정보가 있어야됨)
// User 객체 타입 -> UserDetails 타입 객체
// 즉, Security Session에 Authentication 객체가 있고
// 이 안에 UserDetails 타입의 객체가 있어서 이 것을 통해 User 객체에 접근할 수 o

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import team_project.beer_community.repository.LikeBeerRepository;
import team_project.beer_community.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    @Autowired  //다른 변수를 받는 생성자가 이미 존재하여 @RequiredArgsConstructor가 동작하지 않는다.
    private LikeBeerRepository likeBeerRepository;

    // UserDetails를 구현함으로써 PrincipleDetails는 UserDetails와 같은 타입이됬다.
    private User user; // 콤포지션 변수
    private Map<String, Object> attributes;
    //일반 로그인할때 사용하는 생성자
    public PrincipalDetails(User user){
        this.user = user;
    }

    // 소셜로그인(OAuth2.0사용)할때 사용하는 생성자
    public PrincipalDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    public String getEmail(){
        return (String) attributes.get("email");
    }


    @Override
    public String getName() {
        return (String) attributes.get("name"); // name키에 저장된 값은 google에서 해당 유저의 성명에 해당함(given_name은 이름, family_name은 성)
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    // 해당 user의 권한을 return하는 함수
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole().toString();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 회원이 로그인을 안하면 휴먼계정으로 하기로 한다면?
        // model/User에서  loginDate(로그인한 시점 기록)라는 컬럼이 필요하다
        // logtime = user.getLogindDte
        // now_teim - logtime 이 1년을 초과하면 return false;
        return true;
    }
}
