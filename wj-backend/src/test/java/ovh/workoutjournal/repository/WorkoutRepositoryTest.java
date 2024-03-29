package ovh.workoutjournal.repository;

import ovh.workoutjournal.entity.ExerciseType;
import ovh.workoutjournal.entity.User;
import ovh.workoutjournal.entity.Workout;
import ovh.workoutjournal.entity.WorkoutExercise;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WorkoutRepositoryTest {

    @Autowired
    private WorkoutRepository workoutRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;

    public static final String TEST_EMAIL = "test@email.com";


    @Test
    @DisplayName("Saving workout with Identity Generation ID")
    void testSaveWorkoutBlank() {

        Workout workout = constructTestWorkoutAndUser();
        workoutRepository.save(workout);

        Workout savedWorkout = workoutRepository.findById(workout.getId()).orElse(null);

        assertNotNull(savedWorkout);
        assertTrue(workout.getId() > 0);
        assertEquals(workout.getDate(), savedWorkout.getDate());
    }

    @Test
    @DisplayName("Cascade: saving workout without saving its exercises")
    void testWorkoutExercisesCascade() {

        ExerciseType exerciseType = new ExerciseType(1L,"Lat Raise",null,null,null);
        exerciseTypeRepository.save(exerciseType);

        Workout workout = constructTestWorkoutAndUser();

        for (int i = 1; i < 3; i++) {
            WorkoutExercise we = WorkoutExercise.builder()
                    .exerciseType(exerciseType)
                    .sequenceNumber(i)
                    .parentWorkout(workout)
                    .build();
            workout.getWorkoutExercises().add(we);

        }
        workoutRepository.save(workout);
        Workout savedWorkout = workoutRepository.findById(workout.getId()).orElseThrow();
        assertEquals(2, savedWorkout.getWorkoutExercises().size());
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

        assertEquals(2, workouts.getTotalElements());
        assertEquals(TEST_EMAIL, workouts.getContent().get(0).getUser().getEmail());
        assertTrue(
                workouts.getContent().get(0).getDate().isAfter(
                        workouts.getContent().get(workouts.getSize()-1).getDate())
        );
    }

    @Test
    @DisplayName("Saving workout without user throws Exception")
    void testSavingWorkoutWithoutUser() {

        Workout workout = Workout.builder()
                .date(LocalDateTime.now())
                .workoutExercises(new LinkedList<>())
                .build();

        assertThrows(DataIntegrityViolationException.class,
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