package team_project.beer_community.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "reported_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class ReportedComment extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @NotNull
    @Enumerated(EnumType.STRING)
    private REPORT_TYPE reportType;

    public ReportedComment(User user, Comment comment, REPORT_TYPE reportType) {
        this.user = user;
        this.comment = comment;
        this.reportType = reportType;
    }
}
