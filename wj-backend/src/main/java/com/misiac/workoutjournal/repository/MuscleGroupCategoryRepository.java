package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MuscleGroupCategoryRepository extends JpaRepository<MuscleGroupCategory, Long> {

    Optional<MuscleGroupCategory> findMuscleGroupCategoryByName(String name);
}