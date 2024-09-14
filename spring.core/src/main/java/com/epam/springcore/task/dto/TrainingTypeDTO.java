package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TrainingTypeDTO {

    @NotBlank(message = "Training type name cannot be blank")
    private String name;

}
