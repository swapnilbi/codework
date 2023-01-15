package com.codework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan(basePackages = "com.codework")
@EnableScheduling
@CrossOrigin(origins = "http://localhost:4200")
public class CodeWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeWorkApplication.class, args);
	}
}
