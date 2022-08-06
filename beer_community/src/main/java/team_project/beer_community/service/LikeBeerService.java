package team_project.beer_community.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.LikeBeer;
import team_project.beer_community.domain.User;
import team_project.beer_community.repository.LikeBeerRepository;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
//@NoArgsConstructor  //final을 사용한 필드가 있을 경우 이 어노테이션을 사용할 수 없다.
public class LikeBeerService {
    private final LikeBeerRepository likeBeerRepository;
    private final EntityManager em;

    @Transactional
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

    public List<LikeBeer> findAllWithBeer(Long userid){
        return likeBeerRepository.findLikeBeerWithBeer(userid);
    }

    public List<LikeBeer> findAllWithBeer(){
        return likeBeerRepository.findLikeBeerWithBeer();
    }
}
