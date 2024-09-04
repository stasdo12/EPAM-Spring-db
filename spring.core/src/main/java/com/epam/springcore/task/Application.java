package com.epam.springcore.task;

import com.epam.springcore.task.config.GymAppConfig;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.service.impl.TraineeServiceImpl;
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
		// Создаем контекст приложения на основе конфигурационного класса GymAppConfig
		ApplicationContext context = new AnnotationConfigApplicationContext(GymAppConfig.class);

		// Получаем бин сервиса TraineeService из контекста
		TraineeService traineeService = context.getBean(TraineeService.class);

		// Создаем нового Trainee без имени пользователя и пароля
		User newUser = new User();
		newUser.setFirstName("John");
		newUser.setLastName("Doe");
		newUser.setActive(true); // Активируем пользователя

		Trainee newTrainee = new Trainee();
		newTrainee.setUser(newUser);

		// Сохраняем нового Trainee через сервис
		traineeService.create(newTrainee);

		// Получаем список всех трейни и выводим его
		List<Trainee> allTrainees = traineeService.getTrainees();
		System.out.println("Список всех трейни:");
		allTrainees.forEach(System.out::println);

		// Пример обновления профиля Trainee
		newTrainee.getUser().setLastName("Smith");
		traineeService.update(newTrainee);

		// Получаем обновленный список всех трейни и выводим его
		List<Trainee> updatedTrainees = traineeService.getTrainees();
		System.out.println("Обновленный список всех трейни:");
		updatedTrainees.forEach(System.out::println);

		// Пример удаления профиля Trainee
		traineeService.delete(newTrainee.getTraineeId());

		// Получаем список всех трейни после удаления и выводим его
		List<Trainee> traineesAfterDeletion = traineeService.getTrainees();
		System.out.println("Список всех трейни после удаления:");
		traineesAfterDeletion.forEach(System.out::println);
	}
}