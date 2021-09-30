package pl.sggw.foodsharingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import pl.sggw.foodsharingservice.model.repository.UserRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = UserRepository.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class FoodSharingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodSharingServiceApplication.class, args);
	}

}

