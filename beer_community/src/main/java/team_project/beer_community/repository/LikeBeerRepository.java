package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_project.beer_community.domain.LikeBeer;

public interface LikeBeerRepository extends JpaRepository<LikeBeer, Long> {
}
