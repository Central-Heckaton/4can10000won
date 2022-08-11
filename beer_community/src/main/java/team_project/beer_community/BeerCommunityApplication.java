package team_project.beer_community;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import team_project.beer_community.domain.BEER_TYPE;
import team_project.beer_community.domain.Beer;
import team_project.beer_community.service.BeerService;

@EnableJpaAuditing //@CreatedDate와 같은 어노테이션 작동 가능하게 함
@SpringBootApplication
public class BeerCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerCommunityApplication.class, args);
	}

	@Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
	public BCryptPasswordEncoder encodePassword(){  //password 암호화 시 사용
		return new BCryptPasswordEncoder();
	}

}
