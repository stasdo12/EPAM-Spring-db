package com.epam.springcore.task.dto;

import com.epam.springcore.task.model.TrainingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingResponse {

    private String trainingName;
    private Date trainingDate;
    private TrainingType trainingType;
    private Integer duration;
    private String trainerName;

}
