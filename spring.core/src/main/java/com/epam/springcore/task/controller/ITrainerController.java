package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface ITrainerController {

    @Operation(summary = "Register a new Trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    void registerTrainer(@RequestBody TrainerDTO trainerDTO);

    @Operation(summary = "Get trainer profile by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    ResponseEntity<TrainerDTO> getTrainerProfileByUsername(@PathVariable String username);

    @Operation(summary = "Update trainer profile by username",
            description = "Updates the details of an existing trainer based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainer updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid trainer data")
    })
    ResponseEntity<TrainerDTO> updateTrainerProfile(
            @PathVariable String username,
            @Valid @RequestBody TrainerDTO updatedTrainerDTO);

    @Operation(summary = "Get Trainer Trainings List",
            description = "Retrieves a list of trainings for a specific trainer based on provided criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainings"),
            @ApiResponse(responseCode = "404", description = "Trainer not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<List<TrainingDTO>> getTrainerTrainings(
            @PathVariable("username") String username,
            @RequestParam(value = "from", required = false) LocalDate from,
            @RequestParam(value = "to", required = false) LocalDate to,
            @RequestParam(value = "trainee", required = false) String traineeUsername,
            @RequestParam(value = "trainingType", required = false) String trainingName);

    @Operation(summary = "Activate/Deactivate Trainer",
              description = "Activates or deactivates a trainer based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated/deactivated trainer"),
            @ApiResponse(responseCode = "404", description = "Trainer not found")
    })
    void activateDeactivateTrainee(
            @PathVariable("username") String username,
            @RequestParam("isActive") boolean isActive);
}
