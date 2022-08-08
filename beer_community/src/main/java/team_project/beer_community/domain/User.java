package team_project.beer_community.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter //setter는 개발 단계동안 열어놓는다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //Spring data jpa 사용시 필요(arguments가 없는 생성자 필수)
@ToString(of = {"id", "username", "email"})
@Table(name = "user")
public class User extends BaseTimeEntity{

    @Id @GeneratedValue()
    private Long id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @NotNull
    private String birthday;

    private String imageUrl;

    // 로그인-회원가입 진행을 위해 추가한 필드
    @Enumerated(EnumType.STRING)
    private Role role; // ROLE_USER, ROLE_ADMIN

    private String provider; // google, naver
    private String providerId; // 각 사이트에서 사용자별로 부여된 고유id

    @OneToMany(mappedBy = "user") // User가 삭제되면 User가 작성한 댓글들도 다 삭제됨
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<LikeBeer> likeBeers = new ArrayList<>();

    //회원가입시 사용되는 생성자
    @Builder
    public User(Long id, String email, String password, String username, String birthday, String imageUrl, Role role, String provider, String providerId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.birthday = birthday;
        this.imageUrl = imageUrl;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }

    public User(String email, String password, String username, String birthday, String imageUrl, Role role) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.birthday = birthday;
        this.imageUrl = imageUrl;
        this.role = role;
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
