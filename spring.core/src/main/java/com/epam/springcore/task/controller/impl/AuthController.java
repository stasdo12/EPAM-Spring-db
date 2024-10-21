package com.epam.springcore.task.controller.impl;


import com.epam.springcore.task.controller.IAuthController;
import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.service.impl.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController implements IAuthController {

  private final AuthService authService;

    @Override
    @PostMapping("/auth")
    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

  @Override
  @PostMapping("/logout")
  public  ResponseEntity<Object> logout(@RequestHeader String authorization) {
    String token = authorization.substring(7);
    boolean isLoggedOut = authService.logout(token);
    if (isLoggedOut){
      return ResponseEntity.ok("Logout successful");
    }else {
      return ResponseEntity.badRequest().body("Invalid logout request");
    }
  }
}
