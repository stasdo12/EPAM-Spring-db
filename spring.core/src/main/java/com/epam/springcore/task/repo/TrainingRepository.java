package com.epam.springcore.task.repo;

import com.epam.springcore.task.model.Training;
import com.epam.springcore.task.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTraineeUserUsernameAndDateBetweenAndTrainerUserUsernameAndTrainingTypeName(
            String traineeUsername, LocalDate fromDate, LocalDate toDate, String trainerUsername, String trainingName);

    List<Training> findByTrainerUserUsernameAndDateBetweenAndTraineeUserUsernameAndTrainingName(
            String trainerUsername, LocalDate fromDate, LocalDate toDate, String traineeUsername, String trainingName);

}
