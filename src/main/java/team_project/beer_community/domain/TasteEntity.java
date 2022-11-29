package team_project.beer_community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "taste_entity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TasteEntity {  //Beer가 여러 개의 taste를 가질 수 있도록 OneToMany 매핑을 위한 클래스
    @Id
    @GeneratedValue()
    private Long id;

    //enum type의 taste를 가진다.
    @NotNull
    @Enumerated(EnumType.STRING)
    private Taste taste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    public TasteEntity(Taste taste) {
        this.taste = taste;
    }
}
