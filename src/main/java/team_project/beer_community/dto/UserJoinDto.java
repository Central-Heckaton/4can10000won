package team_project.beer_community.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class UserJoinDto {
    @Email(message = "이메일 입력 양식이 올바르지 않습니다.")
    private String email;

    private String password;

    private String username;

    private String birthday;

    private String imageUrl;
}
