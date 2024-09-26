package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.TrainingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/api/trainings")
public interface ITrainingController {

    @Operation(summary = "Add Training",
            description = "Creates a new training session for a trainee with the specified trainer.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added training"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Trainee or Trainer not found")
    })
    @PostMapping
    ResponseEntity<Void> addTraining(@RequestBody TrainingDTO trainingDTO);
}
