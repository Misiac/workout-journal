package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDateTime;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    Page<Workout> findWorkoutsByUserEmailOrderByDateDesc(String userEmail, Pageable pageable);

    List<WorkoutTiny> findByUserEmailOrderByDateDesc(String userEmail);


    interface WorkoutTiny {
        Long getId();

        LocalDateTime getDate();

        String getName();
    }
}