package com.mj.webmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WebmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebmarketApplication.class, args);
	}

}
