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
//    @Rollback(value = false) //db에 반영
    public void 등록및조회() throws Exception {
        //given
//        User userA = new User("seojio8625@naver.com", "seojo1919@", "서지오", "1998-03-11", null, Role.ROLE_USER);
//        userService.join(userA);
//
//        Beer beerA = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.Lager);
//        beerService.join(beerA);
//
//        Comment commentA = new Comment(userA, "맛있어요", 1);
//        Comment commentB = new Comment(userA, "맛없어요", 5);
//        commentService.join(commentA);
//        commentService.join(commentB);
//
//        beerService.addComment(beerA.getId(), commentA);
//        beerService.addComment(beerA.getId(), commentB);
//
//        TasteEntity tasteEntity_beerA_1 = new TasteEntity(Taste.SOUR);
//        TasteEntity tasteEntity_beerA_2 = new TasteEntity(Taste.SWEET);
//        tasteEntityService.join(tasteEntity_beerA_1);
//        tasteEntityService.join(tasteEntity_beerA_2);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_1);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_2);
//
//        LikeBeer userAlikeBeer = new LikeBeer(beerA);
//        likeBeerService.join(userAlikeBeer);
//        userService.addLikeBeer(userA.getId(), userAlikeBeer);
//
//        em.flush();
//        em.clear();
//
//        //then
//        System.out.println("************************");
//        User user = em.find(User.class, userA.getId());
//        Beer beer = em.find(Beer.class, beerA.getId());
//        System.out.println("commentA id" + commentA.getId());
//        System.out.println("commentB id" + commentB.getId());
//        Comment comment = em.find(Comment.class, commentA.getId());
//
//        assertThat(user.getLikeBeers().size()).isEqualTo(1);
//        assertThat(beer.getComments().size()).isEqualTo(2);
//        assertThat(beer.getTotalPoint()).isEqualTo(3);
//        assertThat(beer.getTastes().size()).isEqualTo(2);
    }

    @Test
//    @Rollback(value = false)
    public void 등록및삭제() throws Exception {
//        User userA = new User("seojio8625@naver.com", "seojo1919@", "서지오", "1998-03-11", null, Role.ROLE_USER);
//        userService.join(userA);
//
//        Beer beerA = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.Lager);
//        beerService.join(beerA);
//
//        Comment commentA = new Comment(userA, "맛있어요", 1);
//        Comment commentB = new Comment(userA, "맛없어요", 5);
//        commentService.join(commentA);
//        commentService.join(commentB);
//
//        beerService.addComment(beerA.getId(), commentA);
//        beerService.addComment(beerA.getId(), commentB);
//
//        TasteEntity tasteEntity_beerA_1 = new TasteEntity(Taste.SOUR);
//        TasteEntity tasteEntity_beerA_2 = new TasteEntity(Taste.SWEET);
//        tasteEntityService.join(tasteEntity_beerA_1);
//        tasteEntityService.join(tasteEntity_beerA_2);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_1);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_2);
//
//
//        LikeBeer userAlikeBeer = new LikeBeer(beerA);
//        likeBeerService.join(userAlikeBeer);
//        userService.addLikeBeer(userA.getId(), userAlikeBeer);
//
//        beerService.deleteComment(beerA.getId(), commentA);
//        beerService.deleteTasteEntity(beerA.getId(), tasteEntity_beerA_1);
//
//        em.flush();
//        em.clear();
//
//        Beer beer = em.find(Beer.class, beerA.getId());
//        assertThat(beer.getTastes().size()).isEqualTo(1);
//        assertThat(beer.getComments().size()).isEqualTo(1);
//
//        User user = em.find(User.class, userA.getId());
//        userService.deleteLikeBeer(user.getId(), beer.getId());
//        assertThat(user.getLikeBeers().size()).isEqualTo(0);
    }

    @Test
//    @Rollback(value = false) //db에 반영
    public void 유저등록() throws Exception {
//        User userA = new User("seojio8625@naver.com", "seojo1919@", "서지오", "1998-03-11", null, Role.ROLE_USER);
//        userService.join(userA);
//
//        Beer beerA = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.Lager);
//        beerService.join(beerA);
//
//        Beer beerB = new Beer("stella", 3.0, 2000, "스텔라입니다.", BEER_TYPE.Lager);
//        beerService.join(beerB);
//
//        Comment commentA = new Comment(userA, "맛있어요", 1);
//        Comment commentB = new Comment(userA, "맛없어요", 5);
//        commentService.join(commentA);
//        commentService.join(commentB);
//
//        beerService.addComment(beerA.getId(), commentA);
//        beerService.addComment(beerA.getId(), commentB);
//
//        TasteEntity tasteEntity_beerA_1 = new TasteEntity(Taste.SOUR);
//        TasteEntity tasteEntity_beerA_2 = new TasteEntity(Taste.SWEET);
//        TasteEntity tasteEntity_beerB_1 = new TasteEntity(Taste.BITTER);
//        tasteEntityService.join(tasteEntity_beerA_1);
//        tasteEntityService.join(tasteEntity_beerA_2);
//        tasteEntityService.join(tasteEntity_beerB_1);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_1);
//        beerService.addTasteEntity(beerA.getId(), tasteEntity_beerA_2);
//        beerService.addTasteEntity(beerB.getId(), tasteEntity_beerB_1);
//
//
//        LikeBeer userAlikeBeer = new LikeBeer(beerA);
//        LikeBeer userAlikeBeer2 = new LikeBeer(beerB);
//        likeBeerService.join(userAlikeBeer);
//        likeBeerService.join(userAlikeBeer2);
//        userService.addLikeBeer(userA.getId(), userAlikeBeer);
//        userService.addLikeBeer(userA.getId(), userAlikeBeer2);
    }
}