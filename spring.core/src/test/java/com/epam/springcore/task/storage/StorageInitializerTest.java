package com.epam.springcore.task.storage;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StorageInitializerTest {

    @Autowired
    private StorageInitializer storageInitializer;

    @Autowired
    private Storage storage;

    @Test
    void init() {

        storageInitializer.init();

        assertThat(storage.getTrainees()).hasSize(2);
        assertThat(storage.getTrainers()).hasSize(2);
        assertThat(storage.getTrainings()).hasSize(2);

        Trainee trainee = storage.getTrainees().get(1L);
        assertThat(trainee).isNotNull();
        assertThat(trainee.getUser().getFirstName()).isEqualTo("Alice");

        Trainer trainer = storage.getTrainers().get(1L);
        assertThat(trainer).isNotNull();
        assertThat(trainer.getUser().getFirstName()).isEqualTo("Charlie");

        Training training = storage.getTrainings().get(1L);
        assertThat(training).isNotNull();
        assertThat(training.getTrainingType()).isEqualTo(TrainingType.CARDIO);
    }
}