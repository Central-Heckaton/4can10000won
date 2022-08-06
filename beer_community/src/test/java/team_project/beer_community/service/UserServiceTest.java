package team_project.beer_community.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.*;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired BeerService beerService;
    @Autowired LikeBeerService likeBeerService;
    @Autowired CommentService commentService;
    @Autowired TasteEntityService tasteEntityService;
    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false) //db에 반영
    public void 등록및조회() throws Exception {
        //given
        User userA = new User("lio8625@naver.com", "seojo1919@", "서지오", "1998-03-11");
        userService.join(userA);

        Beer beerA = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.LARGER);
        beerService.join(beerA);
        
        Comment commentA = new Comment(userA, "맛있어요", 4.5);
        Long c_id = commentService.join(commentA);
        beerService.addComment(beerA.getId(), commentA);

        TasteEntity tasteEntity_beerA_1 = new TasteEntity(Taste.SOUR);
        TasteEntity tasteEntity_beerA_2 = new TasteEntity(Taste.SWEET);
        tasteEntityService.join(tasteEntity_beerA_1);
        tasteEntityService.join(tasteEntity_beerA_2);
        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_1);
        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_2);

        LikeBeer userAlikeBeer = new LikeBeer(beerA);
        likeBeerService.join(userAlikeBeer);
        userService.addLikeBeer(userA.getId(), userAlikeBeer);

        em.flush();
        em.clear();

        //then
        System.out.println("************************");
        User user = em.find(User.class, userA.getId());
        Beer beer = em.find(Beer.class, beerA.getId());
        Comment comment = em.find(Comment.class, commentA.getId());

        assertThat(user.getLikeBeers().size()).isEqualTo(1);
        assertThat(user.getLikeBeers().get(0).getBeer().getId()).isEqualTo(beerA.getId());
        assertThat(user.getLikeBeers().get(0).getBeer().getTastes().get(0).getId()).isEqualTo(tasteEntity_beerA_1.getId());
        assertThat(user.getLikeBeers().get(0).getBeer().getTastes().get(1).getId()).isEqualTo(tasteEntity_beerA_2.getId());
        assertThat(beer.getComments().size()).isEqualTo(1);
        assertThat(user.getLikeBeers().get(0).getBeer().getComments().size()).isEqualTo(1);
        assertThat(beer.getComments().size()).isEqualTo(1);
    }

    @Test
    public void 등록및삭제() throws Exception {

    }
}