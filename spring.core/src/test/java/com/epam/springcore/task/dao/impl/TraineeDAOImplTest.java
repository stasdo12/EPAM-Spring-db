package com.epam.springcore.task.dao.impl;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.User;

import com.epam.springcore.task.storage.impl.MyMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TraineeDAOImplTest {

    private TraineeDAOImpl traineeDAO;

    @BeforeEach
    public void setUp() {

        MyMemoryStorage storage = new MyMemoryStorage();
        traineeDAO = new TraineeDAOImpl(storage.getTrainees());

        User user1 = new User();
        user1.setUserName("user1");
        Trainee trainee1 = new Trainee();
        trainee1.setTraineeId(1L);
        trainee1.setUser(new User());
        traineeDAO.create(trainee1);

        User user2 = new User();
        user2.setUserName("user2");
        Trainee trainee2 = new Trainee();
        trainee2.setTraineeId(2L);
        trainee2.setUser(new User());
        traineeDAO.create(trainee2);
    }

    @Test
    void create_shouldCreateTrainee() {

        Trainee trainee = new Trainee();
        trainee.setTraineeId(3L);
        trainee.setUser(new User());

        Optional<Trainee> result = traineeDAO.create( trainee);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void update_ShouldUpdateTrainee() {

        Trainee trainee = new Trainee();
        trainee.setTraineeId(1L);
        trainee.setUser(new User());

        Optional<Trainee> result = traineeDAO.update(trainee);

        assertTrue(result.isPresent());
        assertEquals(trainee, result.get());
    }

    @Test
    void deleteById_ShouldReturnTrueWhenDeleted() {

        boolean deleted = traineeDAO.deleteById(2L);

        assertTrue(deleted);
        assertThat(traineeDAO.findById(2L)).isNotPresent();
    }

    @Test
    void findById_ShouldReturnTrainee() {

        Optional<Trainee> result = traineeDAO.findById(1L);

        assertTrue(result.isPresent());
        assertThat(result.get().getTraineeId()).isEqualTo(1L);
    }

    @Test
    void findByUsername() {

        Trainee trainee = new Trainee();
        trainee.setTraineeId(3L);
        User user = new User();
        user.setUserName("testUser");
        trainee.setUser(user);
        traineeDAO.create( trainee);

        Optional<Trainee> result = traineeDAO.findByUsername("testUser");

        assertThat(result).isPresent();
        assertThat(result.get().getUser().getUserName()).isEqualTo("testUser");
    }

    @Test
    void getAllTrainees_ShouldReturnListTrainee() {

        List<Trainee> result = traineeDAO.getAllTrainees();

        assertThat(result).hasSize(2);
    }

    @Test
    void findAllByUsername() {

        Trainee trainee1 = new Trainee();
        trainee1.setTraineeId(4L);
        User user1 = new User();
        user1.setUserName("user1");
        trainee1.setUser(user1);
        traineeDAO.create( trainee1);

        Trainee trainee2 = new Trainee();
        trainee2.setTraineeId(5L);
        User user2 = new User();
        user2.setUserName("user1_1");
        trainee2.setUser(user2);
        traineeDAO.create( trainee2);

        List<Trainee> result = traineeDAO.findAllByUsername("user1");

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUser().getUserName()).isEqualTo("user1");
        assertThat(result.get(1).getUser().getUserName()).isEqualTo("user1_1");
    }

}