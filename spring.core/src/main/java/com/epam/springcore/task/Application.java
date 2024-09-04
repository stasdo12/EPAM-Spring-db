package com.epam.springcore.task;

import com.epam.springcore.task.config.GymAppConfig;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TraineeService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {

		ApplicationContext context = new AnnotationConfigApplicationContext(GymAppConfig.class);


		TraineeService traineeService = context.getBean(TraineeService.class);


		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setActive(true);

		Trainee newTrainee = new Trainee();
		newTrainee.setUser(newUser);


		traineeService.create(newTrainee);


		List<Trainee> allTrainees = traineeService.getTrainees();
		System.out.println("Список всех трейни:");
		allTrainees.forEach(System.out::println);


		newTrainee.getUser().setLastName("Smith");
		traineeService.update(newTrainee);


		List<Trainee> updatedTrainees = traineeService.getTrainees();
		System.out.println("Обновленный список всех трейни:");
		updatedTrainees.forEach(System.out::println);


		traineeService.delete(newTrainee.getTraineeId());


		List<Trainee> traineesAfterDeletion = traineeService.getTrainees();
		System.out.println("Список всех трейни после удаления:");
		traineesAfterDeletion.forEach(System.out::println);
	}
}