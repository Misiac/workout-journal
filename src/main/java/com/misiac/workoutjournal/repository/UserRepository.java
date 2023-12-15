package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByEmail(String email);
}