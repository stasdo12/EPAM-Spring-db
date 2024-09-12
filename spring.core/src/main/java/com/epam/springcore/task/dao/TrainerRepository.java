package com.epam.springcore.task.dao;

import com.epam.springcore.task.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findTrainerByUserUsername(String username);
}
