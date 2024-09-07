package com.epam.springcore.task;

import com.epam.springcore.task.utils.PasswordGenerator;
import com.epam.springcore.task.utils.impl.PasswordGenerationImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}
}