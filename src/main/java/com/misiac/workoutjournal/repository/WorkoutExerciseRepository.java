package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
}