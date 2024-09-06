package com.epam.springcore.task.facade;


import com.epam.springcore.task.service.TraineeService;
import com.epam.springcore.task.service.TrainerService;
import com.epam.springcore.task.service.TrainingService;
import org.springframework.stereotype.Component;

@Component
public record GymFacade(TraineeService traineeService,
                        TrainerService trainerService,
                        TrainingService trainingService) {

}
