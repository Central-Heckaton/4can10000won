package team_project.beer_community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.TasteEntity;
import team_project.beer_community.repository.TasteEntityRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TasteEntityService {
    private final TasteEntityRepository tasteEntityRepository;

    @Transactional
    public Long join(TasteEntity tasteEntity){
        tasteEntityRepository.save(tasteEntity);
        return tasteEntity.getId();
    }

    public TasteEntity findOne(Long id){
        return tasteEntityRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<TasteEntity> findAll(){
        return tasteEntityRepository.findAll();
    }
}
