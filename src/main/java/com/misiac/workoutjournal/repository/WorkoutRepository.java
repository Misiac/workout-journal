package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Workout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
}