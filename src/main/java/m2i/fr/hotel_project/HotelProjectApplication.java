package m2i.fr.hotel_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HotelProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelProjectApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200").allowedMethods("*"); //autorise l'accès depuis localhost:4200- GET autorisée
				//registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
				// * dans allow origins : çad autoriser toutes les machines
			}
		};

	}
}
