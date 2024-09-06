package com.epam.springcore.task;

import com.epam.springcore.task.config.GymAppConfig;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TrainerService;
import com.epam.springcore.task.service.TrainingService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.Optional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(GymAppConfig.class);

		TrainingService trainingService = context.getBean(TrainingService.class);
		TrainerService trainerService = context.getBean(TrainerService.class);

		User user = new User();
		user.setUserId(23L);
		user.setFirstName("John");
		user.setLastName("Doe");
		user.setUserName("john.doe");
		user.setPassword("password");
		user.setActive(true);

		Trainer trainer = new Trainer();
		trainer.setTrainerId(1L);
		trainer.setUser(user);


		Trainer updatedTrainer = new Trainer();
		updatedTrainer.setTrainerId(1L);
		updatedTrainer.setUser(user);


		Optional<Trainer> oldTrainer = trainerService.update(updatedTrainer);

		if (oldTrainer.isPresent()) {
			System.out.println("Trainer updated successfully. Old Trainer: " + oldTrainer.get());
		} else {
			System.out.println("Trainer not found, update failed.");
		}
	}
}