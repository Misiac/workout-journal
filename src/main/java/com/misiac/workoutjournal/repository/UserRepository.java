package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}