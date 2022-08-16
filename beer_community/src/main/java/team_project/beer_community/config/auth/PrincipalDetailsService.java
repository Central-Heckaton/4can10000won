package team_project.beer_community.config.auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.UserRepository;

// Security에서 loginProcessUrl("/login"); 요청이 오면
// 자동으로 UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행됨.

//@Transactional
@Service // 해당 어노테이션을 통해 PrincipalDetailService 클래스를 IoC에 등록시킴
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    // Security seesion 안에있는 Authentication 타입객체의 안에 UserDetails 타입객체가 있다.
    // Security session(내부 Authentication(내부 UserDetails))
    // 아래의 함수는 UserDetails를 구현한 PrincipalDetails를 return한다
    // 함수종료시 @AuthenticationPrincipal 어노테이션이 만들어짐
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(email);
//        세큐리티에서 로그인 시 항상 likebeers 초기화
//        userEntity.getLikeBeers().stream().forEach(lb -> lb.getBeer());
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
//        throw  new UsernameNotFoundException(email);
    }
}
