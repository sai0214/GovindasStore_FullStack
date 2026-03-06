package com.eCommerce.GovindasStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.eCommerce")
public class GovindasStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GovindasStoreApplication.class, args);
	}

}
