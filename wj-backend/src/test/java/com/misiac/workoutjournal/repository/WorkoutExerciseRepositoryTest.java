package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WorkoutExerciseRepositoryTest {

    @Autowired
    WorkoutExerciseRepository weRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    WorkoutRepository workoutRepository;

    private Long workoutId;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("test@email.com");
        userRepository.save(user);

        Exercise exercise = new Exercise();
        exercise.setName("Lat Raise");
        exerciseRepository.save(exercise);

        Workout workout = Workout.builder()
                .user(user)
                .date(LocalDateTime.now())
                .workoutExercises(new LinkedList<>())
                .build();
        workoutRepository.save(workout);
        workoutId = workout.getId();
    }

    @Test
    @DisplayName("Update workout exercise")
    void testUpdateWorkoutExercise() {

        Workout workout = workoutRepository.findById(workoutId).orElseThrow();

        WorkoutExercise workoutExercise = constructTestWorkoutExercise(workout);

        workout.getWorkoutExercises().add(workoutExercise);
        workoutRepository.save(workout);

        WorkoutExercise updated = weRepository.findById(workoutExercise.getId()).orElseThrow();
        updated.setReps(10);
        weRepository.save(updated);

        WorkoutExercise findUpdated = weRepository.findById(workoutExercise.getId()).orElseThrow();
        assertEquals(10, findUpdated.getReps());
    }

//
//        // TODO - fix this
//    @Test
//    @DisplayName("Update workout exercise with null value")
//    void testUpdateWithNullValue() {
//
//        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
//        WorkoutExercise workoutExercise = constructTestWorkoutExercise(workout);
//
//        workout.getWorkoutExercises().add(workoutExercise);
//        workoutRepository.save(workout);
//
//        WorkoutExercise updated = weRepository.findById(workoutExercise.getId()).orElseThrow();
//        updated.setReps(null);
//
//        weRepository.save(updated);
//
//        assertThrows(DataIntegrityViolationException.class,
//                () -> weRepository.save(updated));
//        assertEquals(5, weRepository.findById(workoutExercise.getId()).orElseThrow().getReps());
//    }

    @Test
    @DisplayName("Delete workout exercise from Workout")
    void testDeleteExerciseFromWorkout() {

        Workout workout = workoutRepository.findById(workoutId).orElseThrow();

        WorkoutExercise workoutExercise = constructTestWorkoutExercise(workout);

        workout.getWorkoutExercises().add(workoutExercise);
        workoutRepository.save(workout);

        WorkoutExercise saved = weRepository.findById(workoutExercise.getId()).orElseThrow();

        workout.getWorkoutExercises().remove(saved);
        weRepository.delete(saved);

        Workout updatedWorkout = workoutRepository.findById(workoutId).orElseThrow();

        assertTrue(weRepository.findById(workoutExercise.getId()).isEmpty());
        assertTrue(updatedWorkout.getWorkoutExercises().isEmpty());
    }

    private WorkoutExercise constructTestWorkoutExercise(Workout parent) {
        return WorkoutExercise.builder()
                .parentWorkout(parent)
                .exerciseType(exerciseRepository.findExerciseByName("Lat Raise").orElseThrow())
                .load(10.0F)
                .reps(5)
                .setNumber(1)
                .build();
    }
}