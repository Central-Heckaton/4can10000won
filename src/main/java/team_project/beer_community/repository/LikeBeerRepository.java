package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;

import java.util.List;

public interface LikeBeerRepository extends JpaRepository<LikeBeer, Long> {

    @Query("select lb from LikeBeer lb join fetch lb.beer b where lb.user.id = :userid")
    List<LikeBeer> findLikeBeerWithBeer(@Param("userid") Long id);

    @Query("select lb from LikeBeer lb join fetch lb.beer b")
    List<LikeBeer> findLikeBeerWithBeer();

//    @Query("select lb from LikeBeer lb where lb.user =:user")
//    List<LikeBeer> findLikeBeerWithUser(User user);
}
