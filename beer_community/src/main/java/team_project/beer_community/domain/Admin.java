package team_project.beer_community.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter @Setter
public class Admin extends BaseTimeEntity{
    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String admin_name;

    @NotNull
    private String admin_pw;

    @NotNull
    private String admin_email;

    public Admin(String admin_name, String admin_pw, String admin_email) {
        this.admin_name = admin_name;
        this.admin_pw = admin_pw;
        this.admin_email = admin_email;
    }
}
