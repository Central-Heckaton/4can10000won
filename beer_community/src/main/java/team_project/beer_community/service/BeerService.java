package team_project.beer_community.service;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.*;
import team_project.beer_community.dto.BeerDto;
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
        findBeer.setTotalPoint(calTotalPoint(findBeer));
        comment.getUser().addComment(comment);
    }

    @Transactional
    public void deleteComment(Long id, Comment comment){
        Beer findBeer = beerRepository.findById(id).orElseThrow(NullPointerException::new);
        findBeer.getComments().remove(comment);
        comment.getUser().getComments().remove(comment);
        commentRepository.delete(comment);
    }

    @Transactional
    public double calTotalPoint(Beer beer){
        double sum = 0;
        int count = 0;
        try{
            List<Comment> comments = beer.getComments();
            for (Comment comment : comments) {
                if(comment.getParentId() == 0L){ //부모 커멘트인 경우에만 총점 계산 시 고려
                    sum = sum + comment.getPoint();
                    count++;
                }
            }
            return sum / count;
        } catch (Exception exception){
            return 0;
        }
    }

    @Transactional
    public List<BeerDto> findBeersWithBeerName(String beerName){
        List<Beer> beerList = beerRepository.findByBeerNameContaining(beerName);
        List<BeerDto> beerDtoList = new ArrayList<>();

        if(beerList.isEmpty()) return beerDtoList;
        for (Beer beer : beerList) {
            beerDtoList.add(this.converEntityToDto(beer));
        }
        return beerDtoList;
    }
    private BeerDto converEntityToDto(Beer beer){
        return new BeerDto(beer);
    }
}
