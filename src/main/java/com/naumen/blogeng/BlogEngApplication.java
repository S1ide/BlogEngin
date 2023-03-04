package com.naumen.blogeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.naumen.blogeng"})
public class BlogEngApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogEngApplication.class, args);
	}

}
