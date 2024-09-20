package com.epam.springcore.task.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainingTypeDTO {

    @NotBlank(message = "Training type name cannot be blank")
    private String name;

}
