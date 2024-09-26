package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.TrainerDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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


}
