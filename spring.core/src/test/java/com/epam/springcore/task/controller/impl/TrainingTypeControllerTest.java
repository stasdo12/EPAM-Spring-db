package com.epam.springcore.task.controller.impl;

import com.epam.springcore.task.config.TestSecurityConfig;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.service.impl.TrainingTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingTypeController.class)
@Import(TestSecurityConfig.class)
class TrainingTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingTypeService trainingTypeService;

    private List<TrainingTypeDTO> trainingTypeList;

    @BeforeEach
    void setUp() {
        trainingTypeList = new ArrayList<>();
        trainingTypeList.add(new TrainingTypeDTO( "Yoga"));
        trainingTypeList.add(new TrainingTypeDTO( "Pilates"));
    }

    @Test
    void testGetTrainingTypes_Success() throws Exception {
        when(trainingTypeService.getAll()).thenReturn(trainingTypeList);

        mockMvc.perform(get("/training-types")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}