package com.epam.springcore.task.mapper;

import com.epam.springcore.task.dto.TrainerDTO;
import com.epam.springcore.task.dto.TrainingTypeDTO;
import com.epam.springcore.task.dto.UserDTO;
import com.epam.springcore.task.model.*;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class TrainerMapperTest {

    private final TrainerMapper trainerMapper = Mappers.getMapper(TrainerMapper.class);

    @Test
    void shouldMapTrainerToDTO() {

        User user = new User();
        user.setUserId(1L);
        user.setUsername("testuser");

        TrainingType specialization = new TrainingType();
        specialization.setId(1L);
        specialization.setName("Yoga");

        Training training = new Training();
        training.setTrainingId(1L);

        Trainee trainee = new Trainee();
        trainee.setTraineeId(1L);

        Trainer trainer = new Trainer();
        trainer.setTrainerId(1L);
        trainer.setUser(user);
        trainer.setSpecialization(specialization);
        trainer.setTrainings(Collections.singletonList(training));
        trainer.setTrainees(new HashSet<>(Collections.singletonList(trainee)));

        TrainerDTO trainerDTO = trainerMapper.trainerToDTO(trainer);

        assertThat(trainerDTO).isNotNull();
        assertThat(trainerDTO.getUser()).isNotNull();
        assertThat(trainerDTO.getUser().getUsername()).isEqualTo("testuser");
        assertThat(trainerDTO.getSpecialization()).isNotNull();
        assertThat(trainerDTO.getSpecialization().getName()).isEqualTo("Yoga");
    }

    @Test
    void shouldMapTrainerDTOToEntityAndIgnoreFields() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testuser");

        TrainingTypeDTO specializationDTO = new TrainingTypeDTO();
        specializationDTO.setName("Yoga");

        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUser(userDTO);
        trainerDTO.setSpecialization(specializationDTO);
        trainerDTO.setTrainings(Collections.emptyList());
        trainerDTO.setTrainees(Collections.emptySet());

        Trainer trainer = trainerMapper.trainerToEntity(trainerDTO);

        assertThat(trainer).isNotNull();
        assertThat(trainer.getUser()).isNotNull();
        assertThat(trainer.getUser().getUsername()).isEqualTo("testuser");
        assertThat(trainer.getSpecialization()).isNotNull();
        assertThat(trainer.getSpecialization().getName()).isEqualTo("Yoga");
        assertThat(trainer.getTrainings()).isEmpty();
        assertThat(trainer.getTrainees()).isEmpty();
        assertThat(trainer.getTrainerId()).isZero();
    }
}