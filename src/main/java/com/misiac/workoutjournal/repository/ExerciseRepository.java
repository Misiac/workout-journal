package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findExerciseByName(String name);

    Exercise findExerciseById(long exerciseId);


@Query(value = "SELECT e " +
               "FROM Exercise e " +
               "JOIN e.equipmentCategories ec " +
               "WHERE ec.name = :name")
List<Exercise> findExercisesByEquipmentCategories(@Param("name")String name);
}