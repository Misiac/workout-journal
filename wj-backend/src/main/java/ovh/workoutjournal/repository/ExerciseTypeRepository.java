package ovh.workoutjournal.repository;

import ovh.workoutjournal.entity.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseTypeRepository extends JpaRepository<ExerciseType, Long> {

    Optional<ExerciseType> findExerciseByName(String name);

    @Query("SELECT e " +
            "FROM ExerciseType e " +
            "JOIN e.equipmentCategories ec " +
            "WHERE ec.name = :name")
    List<ExerciseType> findExercisesByEquipmentCategory(@Param("name") String name);

    @Query("SELECT e " +
            "FROM ExerciseType e " +
            "JOIN e.muscleGroups mg " +
            "JOIN mg.category c " +
            "WHERE mg.isPrimary = CASE WHEN :isPrimary = true THEN 1 ELSE 0 END " +
            "AND c.name = :name")
    List<ExerciseType> findExercisesByMuscleGroup(@Param("name") String name, @Param("isPrimary") boolean isPrimary);
}