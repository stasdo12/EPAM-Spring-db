package com.epam.springcore.task.dao;


import com.epam.springcore.task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);

    User registerUser(User user);

}
