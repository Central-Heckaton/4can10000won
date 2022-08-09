package team_project.beer_community.config.oauth.provider;

public interface OAuth2UserInfo {
    // getAttributes()를 통해 전달받은 변수들에 접근하기위함
    String getProvider();
    String getProviderId();
    String getEmail();
    String getUsername();
}
