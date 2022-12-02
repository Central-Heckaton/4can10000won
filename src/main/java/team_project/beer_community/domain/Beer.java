package team_project.beer_community.domain;


import lombok.*;
import net.minidev.json.JSONUtil;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "beer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "beerName"})
@Getter @Setter
public class Beer extends BaseTimeEntity {
    @Id @GeneratedValue()
    private Long id;

    @OneToMany(mappedBy = "beer")
    private List<LikeBeer> likeBeers = new ArrayList<>();

    @OneToMany(mappedBy = "beer")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    private List<TasteEntity> tastes = new ArrayList<>();

    @NotNull
    private String beerName;

    @NotNull
    private double alcoholDegree;


    @Column(columnDefinition = "integer default 2500")
    private int price;

    @NotNull
    private String information;

    @NotNull
    @Enumerated(EnumType.STRING)
    private BEER_TYPE beerType;

    private double totalPoint;

    private String imageUrl;

    public Beer(String beerName, double alcoholDegree, int price, String information, BEER_TYPE beerType) {
        this.beerName = beerName;
        this.alcoholDegree = alcoholDegree;
        this.price = price;
        this.information = information;
        this.beerType = beerType;
    }

    //==연관관계 편의 메소드==//
    public void addLikeBeer(LikeBeer likeBeer){
        likeBeers.add(likeBeer);
        likeBeer.setBeer(this);
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setBeer(this);
        System.out.println("comment.getBeer()" + comment.getBeer());
    }

    //==연관관계 편의 메소드==//
    public void addTasteEntity(TasteEntity tasteEntity){
        tastes.add(tasteEntity);
        tasteEntity.setBeer(this);
    }
}
