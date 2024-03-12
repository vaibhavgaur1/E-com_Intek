package com.e_commerce;

import com.e_commerce.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@RequiredArgsConstructor
@Configuration @EnableAutoConfiguration  @ComponentScan
@ComponentScan(basePackages = {"com.e-commerce"})
public class ECommerceApplication implements CommandLineRunner {

	@Autowired
	public UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(ECommerceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userService.initRoleAndUser();
	}
}
