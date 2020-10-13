package ru.bolikov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.bolikov.entity.Product;
import ru.bolikov.exception.rest.ProductErrorResponse;

@SpringBootApplication
public class Lesson6SpringBootApplication {

	@Bean
	public ProductErrorResponse productErrorResponse() {
		return new ProductErrorResponse();
	}

	public static void main(String[] args) {
		SpringApplication.run(Lesson6SpringBootApplication.class, args);
	}

}
