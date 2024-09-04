package com.epam.springcore.task.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JsonDataContainer {

    @JsonProperty("trainees")
    private List<Trainee> trainees;
    @JsonProperty("trainers")
    private List<Trainer> trainers;
    @JsonProperty("trainings")
    private List<Training> trainings;

}
