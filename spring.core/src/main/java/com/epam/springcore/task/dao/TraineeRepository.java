package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Trainee;
import com.epam.springcore.task.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findTraineeByUserUsername(String username);
    void deleteByUserUsername(String username);

}
