package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.MuscleGroup;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ExerciseRepositoryTest {

    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    MuscleGroupCategoryRepository mgcRepository;
    @Autowired
    EquipmentCategoryRepository egcRepository;

    @Test
    @DisplayName("Search for exercise with name")
    void findExerciseByName() {
        Exercise exercise = new Exercise();
        exercise.setName("Lat Raise");
        exerciseRepository.save(exercise);

        Exercise savedExercise = exerciseRepository.findExerciseByName("Lat Raise").orElse(null);

        assertNotNull(savedExercise);
        assertEquals(exercise.getName(), savedExercise.getName());
    }

    @Test
    @DisplayName("Search for exercises with equipment category name")
    void findExercisesByEquipmentCategories() {
        Exercise exercise = new Exercise();
        exercise.setName("Lat Raise");

        EquipmentCategory equipmentCategory = new EquipmentCategory();
        equipmentCategory.setName("Dumbbell");
        egcRepository.save(equipmentCategory);

        exercise.getEquipmentCategories().add(equipmentCategory);
        exerciseRepository.save(exercise);

        Exercise savedExercise = exerciseRepository.findExercisesByEquipmentCategory("Dumbbell").getFirst();

        assertNotNull(savedExercise);
        assertEquals(exercise.getName(), savedExercise.getName());
        assertEquals("Dumbbell", savedExercise.getEquipmentCategories().getFirst().getName());

    }

    @Test
    @DisplayName("Search for exercises with muscle group name")
    void findExercisesByMuscleGroups() {
        Exercise exercise = new Exercise();
        exercise.setName("Lat Raise");

        MuscleGroupCategory mgc = new MuscleGroupCategory("Shoulders");
        mgcRepository.save(mgc);

        MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setCategory(mgc);
        muscleGroup.setIsPrimary((byte) 1);
        muscleGroup.setExercise(exercise);
        exercise.getMuscleGroups().add(muscleGroup);
        exerciseRepository.save(exercise);

        Exercise savedExercise = exerciseRepository.findExercisesByMuscleGroup("Shoulders", true).getFirst();

        assertNotNull(savedExercise);
        assertEquals("Lat Raise", savedExercise.getName());
        assertTrue(savedExercise.getMuscleGroups().contains(muscleGroup));
    }

    @Test
    @DisplayName("Saving Exercise with name exceeding length limit")
    void testTooLongExerciseName() {
        Exercise exercise = new Exercise();
        exercise.setName("This is a very long exercise name that is over 50 characters long");

        assertThrows(
                DataIntegrityViolationException.class, () -> exerciseRepository.save(exercise)
        );
    }
}