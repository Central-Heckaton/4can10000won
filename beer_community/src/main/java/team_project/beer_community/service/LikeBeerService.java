package team_project.beer_community.service;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.repository.LikeBeerRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@NoArgsConstructor
public class LikeBeerService {
    private LikeBeerRepository likeBeerRepository;

    public Long join(LikeBeer likeBeer){
        likeBeerRepository.save(likeBeer);
        return likeBeer.getId();
    }

    public LikeBeer findOnd(Long id){
        return likeBeerRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<LikeBeer> findAll(){
        return likeBeerRepository.findAll();
    }
}
