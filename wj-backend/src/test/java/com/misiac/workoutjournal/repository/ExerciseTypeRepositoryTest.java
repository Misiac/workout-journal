package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.ExerciseType;
import com.misiac.workoutjournal.entity.MuscleGroup;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.LinkedHashSet;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ExerciseTypeRepositoryTest {

    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;
    @Autowired
    MuscleGroupCategoryRepository mgcRepository;
    @Autowired
    EquipmentCategoryRepository egcRepository;

    @Test
    @DisplayName("Search for exercise with name")
    void findExerciseByName() {
        ExerciseType exerciseType = new ExerciseType(1L, "Lat Raise", null, null,null);
        exerciseTypeRepository.save(exerciseType);

        ExerciseType savedExercise = exerciseTypeRepository.findExerciseByName("Lat Raise").orElse(null);

        assertNotNull(savedExercise);
        assertEquals(exerciseType.getName(), savedExercise.getName());
    }

    @Test
    @DisplayName("Search for exercises with equipment category name")
    void findExercisesByEquipmentCategories() {
        ExerciseType exerciseType = new ExerciseType(1L, "Lat Raise",null, new LinkedHashSet<>(), new LinkedHashSet<>());

        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Dumbbell");
        egcRepository.save(equipmentCategory);

        exerciseType.getEquipmentCategories().add(equipmentCategory);
        exerciseTypeRepository.save(exerciseType);

        ExerciseType savedExercise = exerciseTypeRepository.findExercisesByEquipmentCategory("Dumbbell").getFirst();

        assertNotNull(savedExercise);
        assertEquals(exerciseType.getName(), savedExercise.getName());
        assertEquals("Dumbbell", savedExercise.getEquipmentCategories().iterator().next().getName());

    }

    @Test
    @DisplayName("Search for exercises with muscle group name")
    void findExercisesByMuscleGroups() {
        ExerciseType exerciseType = new ExerciseType(1L, "Lat Raise",null, new LinkedHashSet<>(), new LinkedHashSet<>());

        MuscleGroupCategory mgc = new MuscleGroupCategory("Shoulders");
        mgcRepository.save(mgc);

        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setCategory(mgc);
        muscleGroup.setIsPrimary((byte) 1);
        muscleGroup.setExercise(exerciseType);
        exerciseType.getMuscleGroups().add(muscleGroup);
        exerciseType = exerciseTypeRepository.save(exerciseType);

        muscleGroup.setExercise(exerciseType);

        ExerciseType savedExercise = exerciseTypeRepository.findExercisesByMuscleGroup("Shoulders", true).getFirst();

        assertNotNull(savedExercise);
        assertEquals("Lat Raise", savedExercise.getName());
        assertTrue(savedExercise.getMuscleGroups().contains(muscleGroup));
    }

    @Test
    @DisplayName("Saving Exercise with name exceeding length limit")
    void testTooLongExerciseName() {
        ExerciseType exerciseType = new ExerciseType(1L, "Lat Raise",null, null, null);
        exerciseType.setName("This is a very long exercise name that is over 50 characters long");

        assertThrows(
                DataIntegrityViolationException.class, () -> exerciseTypeRepository.save(exerciseType)
        );
    }
}