package com.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExamserverApplication {

//	accessible from Swagger for now:
//	http://localhost:PORT_NUMBER/swagger-ui/index.html#/

	public static void main(String[] args) {
		SpringApplication.run(ExamserverApplication.class, args);
	}
}
