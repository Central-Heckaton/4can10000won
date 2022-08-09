package team_project.beer_community.dto;

import lombok.Data;

@Data
public class UserJoinDto {
    private String email;

    private String password;

    private String username;

    private String birthday;


    private String imageUrl;
}
