package com.example.schooladmin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchoolAdminApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolAdminApiApplication.class, args);
	}

}
