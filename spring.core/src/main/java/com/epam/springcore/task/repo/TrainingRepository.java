package com.epam.springcore.task.repo;

import com.epam.springcore.task.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTraineeUserUserNameAndDateBetween(String traineeUserName, LocalDate fromDate, LocalDate toDate);
    List<Training> findByTrainerUserUserNameAndDateBetween(String trainerUserName, LocalDate fromDate, LocalDate toDate);
}
