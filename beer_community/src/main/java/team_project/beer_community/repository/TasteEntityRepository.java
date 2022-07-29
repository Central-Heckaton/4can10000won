package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_project.beer_community.domain.TasteEntity;

public interface TasteEntityRepository extends JpaRepository<TasteEntity, Long> {
}
