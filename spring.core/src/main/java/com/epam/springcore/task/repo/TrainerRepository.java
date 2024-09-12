package com.epam.springcore.task.repo;

import com.epam.springcore.task.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Trainer findByUserUserName(String userName);

}
