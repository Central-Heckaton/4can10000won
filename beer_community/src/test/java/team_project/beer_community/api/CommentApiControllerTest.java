package team_project.beer_community.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import team_project.beer_community.domain.*;
import team_project.beer_community.service.BeerService;
import team_project.beer_community.service.CommentService;
import team_project.beer_community.service.UserService;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentApiControllerTest {
    @Autowired
    BeerService beerService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void CommentApiControllerTest() throws Exception {
//        User userA = new User("seojio8625@naver.com", "seojo1919@", "서지오", "1998-03-11", null, Role.ROLE_USER);
//        userService.join(userA);
//
//        User userB = new User("lio8625@naver.com", "seojo1919@", "리오", "1998-03-11", null, Role.ROLE_USER);
//        userService.join(userB);
//
//        Beer beerA = new Beer("heineken", 4.5, 4000, "하이네켄입니다.", BEER_TYPE.Lager);
//        beerService.join(beerA);
//
//        Comment commentA = new Comment(userA, "맛있어요", 4.5);
//        commentService.join(commentA);
//
//        Comment commentB = new Comment(userA, "맛있어요", 3.5);
//        commentService.join(commentB);
//
//        Comment commentC = new Comment(userB, "노맛...", commentA.getId());
//        commentService.join(commentC);
//
//
//        beerService.addComment(beerA.getId(), commentA);
//        beerService.addComment(beerA.getId(), commentB);
//        beerService.addComment(beerA.getId(), commentC);
//
//        assertThat(commentC.getParentId()).isEqualTo(commentA.getId());
//        assertThat(beerA.getTotalPoint()).isEqualTo(4);
    }
}