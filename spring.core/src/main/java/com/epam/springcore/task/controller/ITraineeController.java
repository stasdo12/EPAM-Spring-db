package com.epam.springcore.task.controller;

import com.epam.springcore.task.dto.JwtResponse;
import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ITraineeController {

    @Operation(summary = "Register a new Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    JwtResponse registerTrainee(@RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Get trainee profile by Username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid username"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<TraineeDTO> getTraineeProfileByUsername(@PathVariable String username);

    @Operation(summary = "Update trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data")
    })
    ResponseEntity<TraineeDTO> updateTraineeProfile(@PathVariable String username,
                                                    @Valid @RequestBody TraineeDTO trainee);

    @Operation(summary = "delete Trainee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
    })
    void deleteTrainee(@PathVariable String username);

    @Operation(summary = "Get not assigned active trainers for a trainee",
            description = "Retrieves a list of active trainers that are not assigned to the specified trainee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active trainers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid username")
    })
    ResponseEntity<List<TrainerDTO>> getNotAssignedActiveTrainers(@PathVariable String username);

    @Operation(summary = "Get Trainee Trainings List",
            description = "Retrieves a list of trainings for a specific trainee based on provided criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainings"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    ResponseEntity<List<TrainingDTO>> getTraineeTrainings(
            @PathVariable("username") String username,
            @RequestParam(value = "from", required = false) LocalDate from,
            @RequestParam(value = "to", required = false) LocalDate to,
            @RequestParam(value = "trainer", required = false) String trainerUsername,
            @RequestParam(value = "trainingType", required = false) String trainingName);

    @Operation(summary = "Activate/Deactivate Trainee",
            description = "Activates or deactivates a trainee based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully activated/deactivated trainee"),
            @ApiResponse(responseCode = "404", description = "Trainee not found")
    })
    void activateDeactivateTrainee(
            @PathVariable("username") String username,
            @RequestParam("isActive") boolean isActive);

    @Operation(summary = "Update trainee's trainers list",
            description = "Updates the list of trainers for the trainee based on the provided username.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainers updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    ResponseEntity<TraineeDTO> updateTraineeTrainers(
            @PathVariable String username,
            @Valid @RequestBody Set<TrainerDTO> trainers);
}
