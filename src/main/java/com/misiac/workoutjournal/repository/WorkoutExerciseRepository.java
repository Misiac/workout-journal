package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
}