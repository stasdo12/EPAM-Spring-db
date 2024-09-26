package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;



@RequestMapping("/api/trainees")
public interface ITraineeController {

    @Operation(summary = "Register a new Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    ResponseEntity<Object> registerTrainee(@RequestBody TraineeDTO traineeDTO);

    @Operation(summary = "Get trainee profile by Username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee profile retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid username"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{username}")
    ResponseEntity<TraineeDTO> getTraineeProfileByUsername(@PathVariable String username);


    @Operation(summary = "Update trainee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee updated successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data")
    })
    @PutMapping("/{username}")
    ResponseEntity<TraineeDTO> updateTraineeProfile(@PathVariable String username,
                                                    @Valid @RequestBody TraineeDTO trainee);


    @Operation(summary = "delete Trainee profile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trainee deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
    })
    @DeleteMapping("/{username}")
    ResponseEntity<Void> deleteTrainee(@PathVariable String username);


    @Operation(summary = "Get not assigned active trainers for a trainee",
            description = "Retrieves a list of active trainers that are not assigned to the specified trainee.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active trainers retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid username")
    })
    @GetMapping("/{username}/not-assigned-trainers")
    ResponseEntity<List<TrainerDTO>> getNotAssignedActiveTrainers(@PathVariable String username);



    @Operation(summary = "Get Trainee Trainings List",
            description = "Retrieves a list of trainings for a specific trainee based on provided criteria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved trainings"),
            @ApiResponse(responseCode = "404", description = "Trainee not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @GetMapping("{username}/trainings")
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
    @PatchMapping("/{username}/activate")
    ResponseEntity<Void> activateDeactivateTrainee(
            @PathVariable("username") String username,
            @RequestParam("isActive") boolean isActive);

}
