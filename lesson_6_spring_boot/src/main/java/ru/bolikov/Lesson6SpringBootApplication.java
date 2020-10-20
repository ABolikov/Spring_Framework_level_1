package ru.bolikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.bolikov.entity.Product;
import ru.bolikov.exception.rest.ProductErrorResponse;
import ru.bolikov.repositories.UserRepository;
import ru.bolikov.security.UserAuthService;

@SpringBootApplication
public class Lesson6SpringBootApplication {

	@Bean
	public ProductErrorResponse productErrorResponse() {
		return new ProductErrorResponse();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public static void main(String[] args) {
		SpringApplication.run(Lesson6SpringBootApplication.class, args);
	}

}
