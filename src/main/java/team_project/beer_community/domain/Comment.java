package team_project.beer_community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Comment extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "beer_id")
    private Beer beer;

    @OneToMany(mappedBy = "comment")
    private List<ReportedComment> reportedComments = new ArrayList<>();

    private double point = 0;

    private Long parentId = 0L;


    //사용자를 생성자에서 받아야만 comment가 생성할 수 있도록 한다.
    public Comment(User user, String content, double point) {
        this.content = content;
        this.user = user;
        this.point = point;
    }

    public Comment(User user, String content) {
        this.content = content;
        this.user = user;
    }
    public Comment(User user, String content, double point, Long parentId) {
        this.content = content;
        this.user = user;
        this.point = point;
        this.parentId = parentId;
    }

    public Comment(Long parentId, User user, String content) {
        this.content = content;
        this.user = user;
        this.parentId = parentId;
    }

    //=====연관 관계 편의 메소드====//
    public void addReportedComment(ReportedComment reportedComment){
        reportedComments.add(reportedComment);
        reportedComment.setComment(this);
    }
}
