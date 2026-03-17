package com.hotel.hotelav1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = "com.hotel")
@EntityScan(basePackages = {"com.hotel.domains", "com.hotel.domains.enums"})
@EnableJpaRepositories(basePackages = "com.hotel.repositories")
@SpringBootApplication
public class Hotelav1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hotelav1Application.class, args);
	}

}
