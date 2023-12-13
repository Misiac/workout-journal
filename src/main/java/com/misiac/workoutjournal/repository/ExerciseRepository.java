package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Exercise findExerciseByName(String name);

    Exercise findExerciseById(int exerciseId);
}