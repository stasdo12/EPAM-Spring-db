package com.epam.springcore.task.storage;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Trainer;
import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import org.springframework.core.io.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


@SpringBootTest
class StorageInitializerTest {

    @Autowired
    private StorageInitializer storageInitializer;

    @Autowired
    private Storage storage;

    @MockBean
    private Resource resource;

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void onApplicationEvent_ShouldInitializeStorage() throws Exception {

        Resource jsonResource = resourceLoader.getResource("classpath:initialData.json");
        InputStream inputStream = jsonResource.getInputStream();

        when(resource.getInputStream()).thenReturn(inputStream);

        storageInitializer.onApplicationEvent(null);

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