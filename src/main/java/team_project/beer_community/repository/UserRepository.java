package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;

import java.util.Optional;

// CRUD 함수를 JpaRepository가 들고 있음.
// @Repository라는 Annotation이 없어도 IoC됨.
//          이유는 JpaRepositry를 상속했기 때문
public interface UserRepository extends JpaRepository<User, Long> {
    // findBy 규칙 -> Username 문법
    // select * from user where username = 1?
    public User findByUsername(String username); // JPA Query methods참고
    public User findByEmail(String email); // 중복성 체크를 위해 username말고 email을 사용함


    @EntityGraph(attributePaths = "likebeers")
    User findDistinctWithLikeBeersBy();
}
