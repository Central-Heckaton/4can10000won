package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team_project.beer_community.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
