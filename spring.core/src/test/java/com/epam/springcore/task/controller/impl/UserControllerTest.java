package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.dto.PassUsernameDTO;
import com.epam.springcore.task.service.impl.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    private PassUsernameDTO passUsernameDTO;

    @BeforeEach
    void setUp() {
        passUsernameDTO = new PassUsernameDTO();
        passUsernameDTO.setUsername("testUser");
        passUsernameDTO.setPassword("testPassword");
    }


    @Test
    void testLoginSuccess() throws Exception {

        when(userService.matchUserCredentials(any(PassUsernameDTO.class))).thenReturn(true);

        mockMvc.perform(get("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passUsernameDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).matchUserCredentials(any(PassUsernameDTO.class));
    }

    @Test
    void changePassword_ShouldReturnOk_WhenPasswordIsChangedSuccessfully() throws Exception {

        doNothing().when(userService).changeUserPassword(anyString(), anyString());

        mockMvc.perform(put("/login/update-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(passUsernameDTO)))
                .andExpect(status().isOk());

        verify(userService, times(1)).changeUserPassword(passUsernameDTO.getUsername(),
                passUsernameDTO.getPassword());
    }
}