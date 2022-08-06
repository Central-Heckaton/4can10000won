package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_project.beer_community.domain.Beer;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
    //    @Modifying(clearAutomatically = true)
//    @Query("update Beer b set b.totalPoint = :totalPoint where b.id = :id")
//    void updateTotalPoint(@Param("totalPoint") double totalPoint, @Param("id") Long id);
}
