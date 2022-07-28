package team_project.beer_community.domain;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "beerName"})
@Getter @Setter
public class Beer extends BaseTimeEntity {
    @Id @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "beer")
    private List<LikeBeer> likeBeers;

    @OneToMany(mappedBy = "beer")
    private List<Comment> comments;

    @OneToMany(mappedBy = "beer")
    private List<TasteEntity> tastes = new ArrayList<>();

    @NotNull
    private String beerName;

    @NotNull
    private int price;

//    @NotNull
    private String information;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BEER_TYPE beer_type;

//    @NotNull
    private float total_point;

    private String image_url;

    public Beer(String beerName, int price, BEER_TYPE beer_type) {
        this.beerName = beerName;
        this.price = price;
        this.beer_type = beer_type;
    }

    public Beer(String beerName) {
        this.beerName = beerName;
    }

    //==연관관계 편의 메소드==//
    public void addLikeBeer(LikeBeer likeBeer){
        likeBeers.add(likeBeer);
        likeBeer.setBeer(this);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setBeer(this);
    }

    public void addTasteEntity(TasteEntity tasteEntity){
        tastes.add(tasteEntity);
        tasteEntity.setBeer(this);
    }
}
