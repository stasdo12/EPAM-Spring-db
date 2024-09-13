package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTrainee_User_UsernameAndDateBetweenAndTrainer_User_UsernameAndTrainingType_Name(
            String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingName);

    List<Training> findByTrainer_User_UsernameAndDateBetweenAndTrainee_User_UsernameAndTrainingName(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeUsername, String trainingName);
}
