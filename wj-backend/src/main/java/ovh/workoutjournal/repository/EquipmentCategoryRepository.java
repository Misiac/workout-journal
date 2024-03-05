package ovh.workoutjournal.repository;

import ovh.workoutjournal.entity.EquipmentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentCategoryRepository extends JpaRepository<EquipmentCategory, Long> {

    Optional<EquipmentCategory> findEquipmentCategoryByName(String name);
}