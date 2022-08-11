package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import team_project.beer_community.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.user u where c.beer.id = :beerId")
    List<Comment> findCommentWithUser(@Param("beerId") Long beerId);
}
