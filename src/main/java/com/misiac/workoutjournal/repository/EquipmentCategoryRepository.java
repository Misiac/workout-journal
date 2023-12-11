package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Integer> {

    Optional<EquipmentCategory> findEquipmentCategoryByName(String name);
}