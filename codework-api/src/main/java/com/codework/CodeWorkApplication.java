package com.codework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.codework")
public class CodeWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeWorkApplication.class, args);
	}
}
