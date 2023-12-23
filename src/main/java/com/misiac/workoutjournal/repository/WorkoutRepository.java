package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findWorkoutsByUserEmailOrderByDateDesc(String userEmail, Pageable pageable);
}