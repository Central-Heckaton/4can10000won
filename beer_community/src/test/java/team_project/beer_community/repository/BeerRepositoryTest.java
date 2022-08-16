package team_project.beer_community.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.BEER_TYPE;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.domain.Taste;
import team_project.beer_community.domain.TasteEntity;

import java.util.List;

@SpringBootTest
@Transactional
class BeerRepositoryTest {
    @Autowired BeerRepository beerRepository;
    @Autowired TasteEntityRepository tasteEntityRepository;


    @Test
    @Rollback(value = false)
    public void TasteEntityTest() throws Exception {
//        //given
//        Beer beer = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.Lager);
//        TasteEntity tasteEntity1 = new TasteEntity(Taste.SOUR);
//        TasteEntity tasteEntity2 = new TasteEntity(Taste.SWEET);
//        tasteEntityRepository.save(tasteEntity1);
//        tasteEntityRepository.save(tasteEntity2);
//
//        beer.addTasteEntity(tasteEntity1);
//        beer.addTasteEntity(tasteEntity2);
//        beerRepository.save(beer);
//
//        //when
//        List<TasteEntity> result = beer.getTastes();
//        for (TasteEntity tasteEntity : result) {
//            System.out.println("tasteEntity.getTaste() = " + tasteEntity.getTaste());
//        }
//
//        //then
//        Assertions.assertThat(beer.getTastes().get(0).getTaste()).isEqualTo(Taste.SOUR);
//        Assertions.assertThat(beer.getTastes().get(1).getTaste()).isEqualTo(Taste.SWEET);
    }

    @Test
    public void 엔티티컬렉션저장확인() throws Exception {
//        System.out.println("=========hello1=============");
//        List<Beer> result = beerRepository.findAll();
//        System.out.println("result.size() = " + result.size());
//        System.out.println("=========hello2=============");
//        for (Beer beer : result) {
//            System.out.println("=======================" + beer.getTastes().get(0).getTaste());
//            System.out.println("=======================" + beer.getTastes().get(1).getTaste());
//        }
//        System.out.println("=========hello3=============");
    }
}