package com.epam.springcore.task;

import com.epam.springcore.task.config.GymAppConfig;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.storage.Storage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(GymAppConfig.class);

		Storage storage = context.getBean(Storage.class);

		// Получаем и выводим трейни
		Map<Long, Trainee> trainees = storage.getTrainees();
		System.out.println("Трейни:");
		trainees.forEach((id, trainee) -> {
			System.out.println("ID: " + id + ", " + trainee);
			System.out.println("Username: " + trainee.getUser().getUserName());
			System.out.println("Password: " + trainee.getUser().getPassword());
		});

		// Получаем и выводим тренеров
		Map<Long, Trainer> trainers = storage.getTrainers();
		System.out.println("Тренеры:");
		trainers.forEach((id, trainer) -> {
			System.out.println("ID: " + id + ", " + trainer);
		});
	}
}