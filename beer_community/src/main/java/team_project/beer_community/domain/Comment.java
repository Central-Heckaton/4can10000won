package team_project.beer_community.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue
    private int id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @NotNull
    private float point;

    public Comment(String content, User user, Beer beer, float point) {
        this.content = content;
        this.user = user;
        this.beer = beer;
        this.point = point;
    }
}
