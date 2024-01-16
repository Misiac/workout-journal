package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findExerciseByName(String name);

    @Query("SELECT e " +
            "FROM Exercise e " +
            "JOIN e.equipmentCategories ec " +
            "WHERE ec.name = :name")
    List<Exercise> findExercisesByEquipmentCategory(@Param("name") String name);

    @Query("SELECT e " +
            "FROM Exercise e " +
            "JOIN e.muscleGroups mg " +
            "JOIN mg.category c " +
            "WHERE mg.isPrimary = CASE WHEN :isPrimary = true THEN 1 ELSE 0 END " +
            "AND c.name = :name")
    List<Exercise> findExercisesByMuscleGroup(@Param("name") String name, @Param("isPrimary") boolean isPrimary);
}