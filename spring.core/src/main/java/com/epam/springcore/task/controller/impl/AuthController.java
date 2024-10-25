package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.controller.IAuthController;
import com.epam.springcore.task.dto.JwtRequest;
import com.epam.springcore.task.service.impl.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
public class AuthController implements IAuthController {

  private final AuthService authService;

    @Override
    @PostMapping("/auth")
    public Object createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

  @Override
  @PostMapping("/logout")
  @ResponseStatus(HttpStatus.OK)
  public  Object logout(@RequestHeader String authorization) {
    String token = authorization.substring(7);
    boolean isLoggedOut = authService.logout(token);
    if (isLoggedOut){
      return "Logout successful";
    }else {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid logout request");
    }
  }

}
