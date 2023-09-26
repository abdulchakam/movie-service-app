package com.abdulchakam.movieservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "Movie RESTFul API", version = "1.0", description = "OpenAPI for Movie RESTFul API",
				contact = @Contact(name = "Muhammad Abdul Chakam", email = "muhabdulchakam@gmail.com", url = "https://github.com/abdulchakam/movie-service-app")))
public class MovieServiceApplication {

//	Add comment on branch feature A
	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

}
