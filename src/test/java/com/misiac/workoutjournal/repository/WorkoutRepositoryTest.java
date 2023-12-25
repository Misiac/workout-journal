package com.misiac.workoutjournal.repository;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.LinkedList;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ExerciseRepository exerciseRepository;

    public static final String TEST_EMAIL = "test@email.com";


    @Test
    @DisplayName("Saving workout with Identity Generation ID")
    void testSaveWorkoutBlank() {

        Workout workout = constructTestWorkoutAndUser();
        workoutRepository.save(workout);

        Workout savedWorkout = workoutRepository.findById(workout.getId()).orElse(null);

        Assertions.assertNotNull(savedWorkout);
        Assertions.assertTrue(workout.getId() > 0);
        Assertions.assertEquals(workout.getDate(), savedWorkout.getDate());
    }

    @Test
    @DisplayName("Cascade: saving workout without saving its exercises")
    void testWorkoutExercisesCascade() {

        Exercise exercise = new Exercise();
        exercise.setName("Lat Raise");
        exerciseRepository.save(exercise);

        Workout workout = constructTestWorkoutAndUser();

        for (int i = 1; i < 3; i++) {
            WorkoutExercise we = WorkoutExercise.builder()
                    .exerciseType(exercise)
                    .load(2.5f)
                    .reps(5)
                    .setNumber(i)
                    .parentWorkout(workout)
                    .build();
            workout.getWorkoutExercises().add(we);

        }
        workoutRepository.save(workout);
        Workout savedWorkout = workoutRepository.findById(workout.getId()).orElseThrow();
        Assertions.assertEquals(2, savedWorkout.getWorkoutExercises().size());
    }

    @Test
    @DisplayName("Custom searching with Pageable and User Email Desc")
    void testCustomSearchingWithUserEmailAndPageable() throws InterruptedException {

        Workout workout1 = constructTestWorkoutAndUser();
        Thread.sleep(500);
        Workout workout2 = Workout.builder()
                .user(workout1.getUser())
                .date(LocalDateTime.now())
                .workoutExercises(new LinkedList<>())
                .build();

        workoutRepository.save(workout1);
        workoutRepository.save(workout2);

        Pageable pageable = Pageable.ofSize(5);
        Page<Workout> workouts = workoutRepository.findWorkoutsByUserEmailOrderByDateDesc(TEST_EMAIL, pageable);

        Assertions.assertEquals(2, workouts.getTotalElements());
        Assertions.assertEquals(TEST_EMAIL, workouts.getContent().getFirst().getUser().getEmail());
        Assertions.assertTrue(
                workouts.getContent().getFirst().getDate().isAfter(
                        workouts.getContent().getLast().getDate())
        );
    }

    @Test
    @DisplayName("Saving workout without user throws Exception")
    void testSavingWorkoutWithoutUser() {

        Workout workout = Workout.builder()
                .date(LocalDateTime.now())
                .workoutExercises(new LinkedList<>())
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class,
                () -> workoutRepository.save(workout)
        );
    }

    private Workout constructTestWorkoutAndUser() {
        User user = new User();
        user.setEmail(TEST_EMAIL);

        Workout workout = Workout.builder()
                .user(user)
                .date(LocalDateTime.now())
                .workoutExercises(new LinkedList<>())
                .build();
        userRepository.save(user);

        return workout;
    }
}