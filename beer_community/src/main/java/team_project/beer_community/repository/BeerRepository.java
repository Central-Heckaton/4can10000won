package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_project.beer_community.domain.Beer;

import java.util.Optional;

public interface BeerRepository extends JpaRepository<Beer, Long> {
//    public Beer findById(Long id);

}
