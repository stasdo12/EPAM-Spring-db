package com.epam.springcore.task.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JsonDataContainer {

    private List<Trainee> trainees;
    private List<Trainer> trainers;
    private List<Training> trainings;

}
