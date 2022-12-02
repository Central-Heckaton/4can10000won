package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_project.beer_community.domain.Beer;

import java.util.List;
import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    List<Beer> findByBeerNameContaining(String beerName);
    // JpaRepository에서는 By 뒷부분은 SQL의 where조건 절에 해당된다. 따라서, Containing을 붙여주면 Like 검색이 된다.

    //    @Modifying(clearAutomatically = true)
//    @Query("update Beer b set b.totalPoint = :totalPoint where b.id = :id")
//    void updateTotalPoint(@Param("totalPoint") double totalPoint, @Param("id") Long id);

}
