package com.epam.springcore.task.controller;

import com.epam.springcore.task.dto.TrainingTypeDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/api/training-types")
public interface ITrainingTypeController {

    @Operation(summary = "Get all training types", description = "Retrieves a list of all training types.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved training types")
    })
    @GetMapping
    ResponseEntity<List<TrainingTypeDTO>> getTrainingTypes();

}
