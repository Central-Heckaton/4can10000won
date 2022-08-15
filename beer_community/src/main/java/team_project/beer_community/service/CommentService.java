package team_project.beer_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.repository.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    /**
     *
     */
    @Transactional
    public Long join(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    @Transactional
    public Long delete(Comment comment){
        commentRepository.delete(comment);
        return comment.getId();
    }

    public Comment findOne(Long id){
        return commentRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    @Transactional
    public void updatePoint(Long id, int point){
        Comment findComment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
        findComment.setPoint(point);
    }

    @Transactional
    public void updateContent(Long id, String content){
        Comment findComment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
        findComment.setContent(content);
    }

    public List<Comment> findAllWithUser(Long beerId) {
        return commentRepository.findCommentWithUser(beerId);
    }

    public List<Comment> findAllRecomments(Long parentCommentId) {
        return commentRepository.findRecommentsWithParentId(parentCommentId);
    }
}
