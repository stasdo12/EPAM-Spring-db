package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingRepository extends JpaRepository<Training, Long> {

    List<Training> findByTrainee_User_UsernameAndDateBetween(String username, LocalDate fromDate, LocalDate toDate);

    List<Training> findByTrainer_User_UsernameAndDateBetween(String username, LocalDate fromDate, LocalDate toDate);

}
