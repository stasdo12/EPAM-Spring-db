package com.epam.springcore.task.controller;

import com.epam.springcore.task.dto.JwtRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthController {

    @Operation(summary = "Authenticate a user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful, JWT token generated"),
            @ApiResponse(responseCode = "400", description = "Invalid username or password"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Object createAuthToken(@RequestBody JwtRequest authRequest);

    @Operation(summary = "Logout",
            description = "Logs out the user and invalidates the JWT token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    Object logout(String authorization);

}
