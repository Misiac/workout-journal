package com.misiac.workoutjournal.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class WorkoutExerciseRepositoryTest {

    @Autowired
    WorkoutExerciseRepository weRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ExerciseTypeRepository exerciseTypeRepository;
    @Autowired
    WorkoutRepository workoutRepository;

    private Long workoutId;

//    @BeforeEach
//    void setUp() {
//        User user = new User();
//        user.setEmail("test@email.com");
//        userRepository.save(user);
//
//        ExerciseType exercise = new ExerciseType();
//        exercise.setName("Lat Raise");
//        exerciseTypeRepository.save(exercise);
//
//        Workout workout = Workout.builder()
//                .user(user)
//                .date(LocalDateTime.now())
//                .workoutExercises(new LinkedList<>())
//                .build();
//        workoutRepository.save(workout);
//        workoutId = workout.getId();
//    }
//
//    @Test
//    @DisplayName("Update workout exercise")
//    void testUpdateWorkoutExercise() {
//
//        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
//
//        WorkoutExercise workoutExercise = constructTestWorkoutExercise(workout);
//
//        workout.getWorkoutExercises().add(workoutExercise);
//        workoutRepository.save(workout);
//
//        WorkoutExercise updated = weRepository.findById(workoutExercise.getId()).orElseThrow();
//        updated.setReps(10);
//        weRepository.save(updated);
//
//        WorkoutExercise findUpdated = weRepository.findById(workoutExercise.getId()).orElseThrow();
//        assertEquals(10, findUpdated.getReps());
//    }
//
//    @Test
//    @DisplayName("Delete workout exercise from Workout")
//    void testDeleteExerciseFromWorkout() {
//
//        Workout workout = workoutRepository.findById(workoutId).orElseThrow();
//
//        WorkoutExercise workoutExercise = constructTestWorkoutExercise(workout);
//
//        workout.getWorkoutExercises().add(workoutExercise);
//        workoutRepository.save(workout);
//
//        WorkoutExercise saved = weRepository.findById(workoutExercise.getId()).orElseThrow();
//
//        workout.getWorkoutExercises().remove(saved);
//        weRepository.delete(saved);
//
//        Workout updatedWorkout = workoutRepository.findById(workoutId).orElseThrow();
//
//        assertTrue(weRepository.findById(workoutExercise.getId()).isEmpty());
//        assertTrue(updatedWorkout.getWorkoutExercises().isEmpty());
//    }
//
//    private WorkoutExercise constructTestWorkoutExercise(Workout parent) {
//        return WorkoutExercise.builder()
//                .parentWorkout(parent)
//                .exerciseType(exerciseTypeRepository.findExerciseByName("Lat Raise").orElseThrow())
//                .load(10.0F)
//                .reps(5)
//                .setNumber(1)
//                .build();
//    }
}