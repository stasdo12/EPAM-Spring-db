package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.User;
import com.epam.springcore.task.storage.impl.MyMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerDAOImplTest {

    private TrainerDAOImpl trainerDAO;

    @BeforeEach
    public void setUp(){
        MyMemoryStorage storage = new MyMemoryStorage();
        trainerDAO = new TrainerDAOImpl(storage.getTrainers());
        User user1 = new User();
        user1.setUserName("trainer1");
        Trainer trainer1 = new Trainer();
        trainer1.setTrainerId(1L);
        trainer1.setUser(user1);
        trainerDAO.create(1L, trainer1);

        User user2 = new User();
        user2.setUserName("trainer2");
        Trainer trainer2 = new Trainer();
        trainer2.setTrainerId(2L);
        trainer2.setUser(user2);
        trainerDAO.create(2L, trainer2);
    }

    @Test
    void create_shouldCreateTrainer() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(3L);
        trainer.setUser(new User());

        Optional<Trainer> result = trainerDAO.create(3L, trainer);

        assertTrue(result.isPresent());
        assertEquals(trainer, result.get());
    }

    @Test
    void update_ShouldUpdateTrainer() {
        Trainer existingTrainer = new Trainer();
        existingTrainer.setTrainerId(1L);
        User existingUser = new User();
        existingUser.setUserName("trainer1");
        existingTrainer.setUser(existingUser);
        trainerDAO.create(1L, existingTrainer);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setTrainerId(1L);
        updatedTrainer.setUser(new User());

        Optional<Trainer> result = trainerDAO.update(updatedTrainer);

        assertTrue(result.isPresent());
        assertThat(result.get().getTrainerId()).isEqualTo(1L);
        assertThat(result.get().getUser().getUserName()).isEqualTo("trainer1");
    }

    @Test
    void getById_ShouldReturnTrainer() {
        Optional<Trainer> result = trainerDAO.getById(1L);

        assertTrue(result.isPresent());
        assertThat(result.get().getTrainerId()).isEqualTo(1L);
    }

    @Test
    void getByUsername() {
        Trainer trainer = new Trainer();
        trainer.setTrainerId(3L);
        User user = new User();
        user.setUserName("testTrainer");
        trainer.setUser(user);
        trainerDAO.create(3L, trainer);

        Optional<Trainer> result = trainerDAO.getByUsername("testTrainer");

        assertThat(result).isPresent();
        assertThat(result.get().getUser().getUserName()).isEqualTo("testTrainer");
    }

    @Test
    void getAllTrainers_ShouldReturnListOfTrainers() {
        List<Trainer> result = trainerDAO.getAllTrainers();

        assertThat(result).hasSize(2);
    }

    @Test
    void getAllTrainersByUsername() {
        List<Trainer> result = trainerDAO.getAllTrainersByUsername("trainer1");

        assertThat(result).hasSize(1);
        assertThat(result).extracting(trainer -> trainer.getUser().getUserName())
                .containsExactly("trainer1");
    }

    @Test
    void getMaxId() {
        long maxId = trainerDAO.getMaxId();
        assertThat(maxId).isEqualTo(3L);
    }
}