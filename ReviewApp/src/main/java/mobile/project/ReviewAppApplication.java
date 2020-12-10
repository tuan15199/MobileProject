package mobile.project;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.tomcat.jni.Address;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import mobile.project.dtos.UserSignUp;
import mobile.project.models.Roles;
import mobile.project.models.User;
import mobile.project.services.UserService;

@SpringBootApplication
public class ReviewAppApplication implements CommandLineRunner {

	@Autowired
	UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ReviewAppApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//	    return new BCryptPasswordEncoder();
//	}

	@Override
	public void run(String... args) throws Exception {
		userService.delete("tuan");

		UserSignUp admin = new UserSignUp();
		admin.setUsername("tuan");
		admin.setPassword("tuan");
		admin.setAddressDetail("20/11 Nguyen Van Luong");
		admin.setAddressDistrict("Go Vap");
		admin.setAddressCity("Ho Chi Minh");

		userService.signup(admin, new ArrayList<Roles>(Arrays.asList(Roles.ROLE_ADMIN)));
	}

	@Bean
	public WebMvcConfigurer CORSConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
			}
		};
	}
}
