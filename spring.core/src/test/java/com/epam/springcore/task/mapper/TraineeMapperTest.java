package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TraineeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;


class TraineeMapperTest {

    private final TraineeMapper traineeMapper = Mappers.getMapper(TraineeMapper.class);

    @Test
    void shouldMapTraineeToDTO() {

        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");
        user.setPassword("password123");
        user.setActive(true);

        Training training = new Training();
        training.setTrainingId(1L);

        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);

        Trainee trainee = new Trainee();
        trainee.setTraineeId(1L);
        trainee.setUser(user);
        trainee.setBirthday(LocalDate.of(2000, 1, 1));
        trainee.setAddress("123 Test Street");
        trainee.setTrainings(Collections.singletonList(training));
        trainee.setTrainers(new HashSet<>(Collections.singletonList(trainer)));

        TraineeDTO traineeDTO = traineeMapper.traineeToDTO(trainee);

        assertThat(traineeDTO).isNotNull();
        assertThat(traineeDTO.getUser()).isNotNull();
        assertThat(traineeDTO.getUser().getUsername()).isEqualTo("testuser");
        assertThat(traineeDTO.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(traineeDTO.getAddress()).isEqualTo("123 Test Street");
        assertThat(traineeDTO.getTrainings()).isNull();
        assertThat(traineeDTO.getTrainers()).isNull();
    }

    @Test
    void shouldMapTraineeDTOToEntityAndIgnoreFields() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUser(userDTO);
        traineeDTO.setBirthday(LocalDate.of(2000, 1, 1));
        traineeDTO.setAddress("123 Test Street");
        traineeDTO.setTrainings(Collections.emptyList());
        traineeDTO.setTrainers(Collections.emptyList());

        Trainee trainee = traineeMapper.traineeToEntity(traineeDTO);

        assertThat(trainee.getUser()).isNotNull();
        assertThat(trainee.getUser().getUsername()).isEqualTo("testuser");
        assertThat(trainee.getBirthday()).isEqualTo(LocalDate.of(2000, 1, 1));
        assertThat(trainee.getAddress()).isEqualTo("123 Test Street");
        assertThat(trainee.getTrainings()).isEmpty();
        assertThat(trainee.getTrainers()).isEmpty();
        assertThat(trainee.getTraineeId()).isZero();
    }
}