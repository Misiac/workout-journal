package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

    Optional<Exercise> findExerciseByName(String name);

    Exercise findExerciseById(int exerciseId);

@Query(value = "SELECT e.name " +
               "FROM exercises e " +
               "JOIN equipment_jointable ej ON e.id = ej.exercise_id " +
               "JOIN exercise_equipment_categories ec ON ej.category_id = ec.id " +
               "WHERE ec.name = :name", nativeQuery = true)
List<String> findExercisesByEquipmentCategoriesNative(String name);

@Query(value = "SELECT e " +
               "FROM Exercise e " +
               "JOIN e.equipmentCategories ec " +
               "WHERE ec.name = :name")
List<Exercise> findExercisesByEquipmentCategories(String name);
}