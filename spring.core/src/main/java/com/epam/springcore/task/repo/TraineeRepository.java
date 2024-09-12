package com.epam.springcore.task.repo;

import com.epam.springcore.task.model.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Trainee findByUserUserName(String userName);
}
