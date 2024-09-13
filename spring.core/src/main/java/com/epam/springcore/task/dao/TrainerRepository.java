package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findTrainerByUserUsername(String username);


    @Query("SELECT t FROM Trainer t WHERE t.trainerId NOT IN " +
            "(SELECT tr.trainer.trainerId FROM Training tr WHERE tr.trainee.traineeId = " +
            "(SELECT t2.traineeId FROM Trainee t2 WHERE t2.user.username = :traineeUsername))")
    List<Trainer> findTrainersNotAssignedToTrainee(@Param("traineeUsername") String traineeUsername);
}
