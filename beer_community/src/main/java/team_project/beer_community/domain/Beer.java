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

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    private List<LikeBeer> likeBeers;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    private List<TasteEntity> tastes = new ArrayList<>();

    @NotNull
    private String beerName;

    @NotNull
    private float alcoholDegree;

    @NotNull
    private int price;

//    @NotNull
    private String information;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BEER_TYPE beerType;

//    @NotNull
    private float totalPoint = calTotalPoint();

    private String imageUrl;

    public Beer(String beerName, float alcoholDegree, int price, String information, BEER_TYPE beerType) {
        this.beerName = beerName;
        this.alcoholDegree = alcoholDegree;
        this.price = price;
        this.information = information;
        this.beerType = beerType;
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

    //==연관관계 편의 메소드==//
    public void addTasteEntity(TasteEntity tasteEntity){
        tastes.add(tasteEntity);
        tasteEntity.setBeer(this);
    }

    //==total_point 계산==//
    public float calTotalPoint(){
        float sum = 0;
        for (Comment comment : comments) {
           sum = sum + comment.getPoint();
        }
        return sum / comments.size();
    }
}
