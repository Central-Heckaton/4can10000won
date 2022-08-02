package team_project.beer_community.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.*;
import team_project.beer_community.repository.BeerRepository;
import team_project.beer_community.repository.CommentRepository;
import team_project.beer_community.repository.LikeBeerRepository;
import team_project.beer_community.repository.TasteEntityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BeerService {
    private final BeerRepository beerRepository;
    private final TasteEntityRepository tasteEntityRepository;
    private final LikeBeerRepository likeBeerRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Long join(Beer beer){
        beerRepository.save(beer);
        return beer.getId();
    }

    public List<Beer> findBeers(){
        return beerRepository.findAll();
    }

    public Beer findOne(Long id){
        return beerRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    public List<Taste> findAllTaste(Long id){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        List<TasteEntity> tasteEntityList = findBeer.getTastes();
        List<Taste> tasteList = new ArrayList<>();
        for (TasteEntity tasteEntity : tasteEntityList) {
            tasteList.add(tasteEntity.getTaste());
        }
        return tasteList;
    }

    @Transactional
    public void updateInformation(Long id, String information){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.setInformation(information);
    }

    @Transactional
    public void updatePrice(Long id, int price){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.setPrice(price);
    }

    @Transactional
    public void addTasteEntity(Long id, TasteEntity tasteEntity){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.addTasteEntity(tasteEntity);
    }

    @Transactional
    public void deleteTasteEntity(Long id, TasteEntity tasteEntity) {
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.getTastes().remove(tasteEntity);
        tasteEntityRepository.delete(tasteEntity);
    }

    @Transactional
    public void addComment(Long id, Comment comment){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.addComment(comment);
        User findUser = comment.getUser();
        findUser.addComment(comment);
    }

    @Transactional
    public void deleteComment(Long id, Comment comment){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.getComments().remove(comment);
        comment.getUser().getComments().remove(comment);
        commentRepository.delete(comment);
    }
}
