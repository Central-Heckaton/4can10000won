package team_project.beer_community.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.Comment;
import team_project.beer_community.repository.CommentRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@NoArgsConstructor
public class CommentService {
    private CommentRepository commentRepository;

    /**
     *
     */
    public Long join(Comment comment){
        commentRepository.save(comment);
        return comment.getId();
    }

    public Comment findOne(Long id){
        return commentRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<Comment> findAll(){
        return commentRepository.findAll();
    }

    public void updatePoint(Long id, int point){
        Comment findComment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
        findComment.setPoint(point);
    }

    public void updateContent(Long id, String content){
        Comment findComment = commentRepository.findById(id).orElseThrow(NullPointerException::new);
        findComment.setContent(content);
    }
}
