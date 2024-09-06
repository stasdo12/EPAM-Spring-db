package com.epam.springcore.task;

import com.epam.springcore.task.config.GymAppConfig;
import com.epam.springcore.task.model.Trainee;

import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TraineeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


import java.util.Optional;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(GymAppConfig.class);

		TraineeService traineeService = context.getBean(TraineeService.class);


		User traineeUser = new User();
		traineeUser.setUserId(1L);
		traineeUser.setFirstName("Alice");
		traineeUser.setLastName("Smith");
		traineeUser.setActive(true);

		Trainee trainee = new Trainee();
		trainee.setTraineeId(3L);
		trainee.setUser(traineeUser);

		Optional<Trainee> createdTrainee = traineeService.create(trainee);

		if (createdTrainee.isPresent()) {
			System.out.println("Trainee created successfully: " + createdTrainee.get());
		} else {
			System.out.println("Failed to create trainee.");
		}

		// Update an existing Trainee
		Trainee updatedTrainee = new Trainee();
		updatedTrainee.setTraineeId(3L); // Use the ID of the created trainee
		updatedTrainee.setUser(traineeUser);

		Optional<Trainee> oldTrainee = traineeService.update(updatedTrainee);

		if (oldTrainee.isPresent()) {
			System.out.println("Trainee updated successfully. Old Trainee: " + oldTrainee.get());
		} else {
			System.out.println("Trainee not found, update failed.");
		}
	}
}