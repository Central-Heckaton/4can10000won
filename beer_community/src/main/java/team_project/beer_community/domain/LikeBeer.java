package team_project.beer_community.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "like_beer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class LikeBeer {

    @Id @GeneratedValue
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    public LikeBeer(int id, User user, Beer beer) {
        this.id = id;
        this.user = user;
        this.beer = beer;
    }
}