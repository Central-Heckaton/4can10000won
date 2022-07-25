package team_project.beer_community.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //setter는 개발 단계동안 열어놓는다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //Spring data jpa 사용시 필요
@ToString(of = {"id", "username"})
@Table(name = "user")
public class User {

    @Id @GeneratedValue
    private int id;

    @NotNull
    private String userEmail;

    @NotNull
    private String userPw;

    @NotNull
    private String userName;

//    @NotNull
    private LocalDate birthday;

//    @NotNull
    private LocalDateTime createdDate;

    private String imageUrl;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeBeer> likeBeers = new ArrayList<>();

    public User(String userEmail, String userPw, String userName) {
        this.userEmail = userEmail;
        this.userPw = userPw;
        this.userName = userName;
    }

    //==연관관계 편의 메소드==//
    public void addLikeBeer(LikeBeer likeBeer){
        likeBeers.add(likeBeer);
        likeBeer.setUser(this);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setUser(this);
    }
}
