package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.TraineeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RequestMapping("/api/trainees")
public interface ITraineeController {

    @Operation(summary = "Register a new trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid trainee data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    ResponseEntity<Object> registerTrainee(@RequestBody TraineeDTO traineeDTO);
}
