package team_project.beer_community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;
import team_project.beer_community.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("select c from Comment c join fetch c.user u where c.beer.id = :beerId and c.parentId = 0L")
    List<Comment> findCommentWithUser(@Param("beerId") Long beerId);

    @Query("select c from Comment c where c.parentId =:parentCommentId")
    List<Comment> findRecommentsWithParentId(@Param("parentCommentId") Long parentCommentId);
}
