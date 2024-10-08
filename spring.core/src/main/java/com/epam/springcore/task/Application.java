package com.epam.springcore.task;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.springcore")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}