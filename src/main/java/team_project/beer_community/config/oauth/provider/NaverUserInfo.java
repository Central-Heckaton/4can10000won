package team_project.beer_community.config.oauth.provider;

import java.util.Map;

public class NaverUserInfo implements OAuth2UserInfo{
    // oAuth2User.getAttributes() = {resultcode=00, message=success, response={id=gemgDkrwZwCX-gA3TE7mHR0OAW4doONhq7ZUTonE1YU, nickname=고경환, profile_image=https://phinf.pstatic.net/contact/20220316_119/1647425542931YWABt_JPEG/image.jpg, email={이메일}, name=고경환, birthday=12-07, birthyear=1998}}
    // 중에서 response={id=gemgDkrwZwCX-gA3TE7mHR0OAW4doONhq7ZUTonE1YU, nickname=고경환, profile_image=https://phinf.pstatic.net/contact/20220316_119/1647425542931YWABt_JPEG/image.jpg, email={이메일}, name=고경환, birthday=12-07, birthyear=1998}}
    // 만 augments로 받아서 생성자로 초기화시킴.
    private Map<String, Object> attributes; // oauth2User.getAttributes()

    public NaverUserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return (String) attributes.get("id");
    }

    @Override
    public String getProviderId() {
        return "naver";
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getUsername() {
        return (String) attributes.get("name");
    }

    public String getProfileImage() {return (String) attributes.get("profile_image");}

    public String getBirthDay() {return (String) attributes.get("birthyear") + "-" + (String) attributes.get("birthday");}

}
