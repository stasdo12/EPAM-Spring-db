package com.epam.springcore.task.controller;


import com.epam.springcore.task.dto.PassUsernameDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
public interface IUserController {

    @Operation(summary = "Login a Trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "400", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    ResponseEntity<Void> login(@Valid @RequestBody PassUsernameDTO passUsernameDTO);

    @Operation(summary = "Change user password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid password format")
    })
    @PutMapping("/update-password")
    ResponseEntity<Void> changePassword(@Valid @RequestBody PassUsernameDTO passUsernameDTO );
}
