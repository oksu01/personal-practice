package com.none.no_name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class NoNameApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoNameApplication.class, args);
	}

}
