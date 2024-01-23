package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutExerciseRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private WorkoutMapper workoutMapper;

    @InjectMocks
    WorkoutService workoutService;

    @Test
    @DisplayName("addWorkout normal conditions")
    void testAddWorkout() {

        Workout workout = new Workout();
        WorkoutRequest workoutRequest = new WorkoutRequest();

        when(workoutMapper.toWorkout(any(WorkoutRequest.class), anyString())).thenReturn(workout);
        workoutService.addWorkout(workoutRequest, "email");

        verify(workoutRepository, times(1)).save(workout);
    }

    @Test
    @DisplayName("getWorkouts normal conditions")
    void testGetWorkoutsForUser() {

        String email = "test@email.com";
        Pageable pageable = Pageable.ofSize(5);

        workoutService.getWorkoutsForUser(email, pageable);
        verify(workoutRepository, times(1))
                .findWorkoutsByUserEmailOrderByDateDesc(eq(email), eq(pageable));
    }

    @Test
    @DisplayName("deleteWorkout normal conditions")
    void testDeleteWorkout() {
        Workout workout = new Workout();
        User user = new User();
        user.getWorkouts().add(workout);
        Long workoutId = 5L;
        String email = "test@email.com";

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(userRepository.findUserByEmail(email)).thenReturn(user);

        workoutService.deleteWorkout(email, workoutId);

        verify(userRepository, times(1)).findUserByEmail(eq(email));
        verify(workoutRepository, times(1)).delete(eq(workout));
    }

    @Test
    @DisplayName("deleteWorkout from different user")
    void testDeleteWorkoutDifferentUser() {
        User user1 = new User();
        User user2 = new User();
        Workout workout = new Workout();
        user1.getWorkouts().add(workout);
        Long workoutId = 5L;
        String email = "user2@email.com";

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(userRepository.findUserByEmail(email)).thenReturn(user2);

        assertThrows(UnauthorizedException.class,
                () -> workoutService.deleteWorkout(email, workoutId)
        );
        verify(workoutRepository, times(0)).delete(workout);
    }


//    @Test
//    @DisplayName("Update WorkoutExercise from ExerciseRequest")
//    void testUpdateExercise() {
//        User user = new User();
//        Workout workout = new Workout();
//        workout.setUser(user);
//
//        WorkoutExercise workoutExercise = new WorkoutExercise();
//        Exercise exercise = new Exercise();
//        exercise.setId(2L);
//        workoutExercise.setReps(2);
//        workoutExercise.setLoad(2F);
//        workoutExercise.setExerciseType(exercise);
//        workoutExercise.setParentWorkout(workout);
//
//        Exercise newExercise = new Exercise();
//        newExercise.setId(5L);
//        ExerciseRequest exerciseRequest = new ExerciseRequest(5L, 5F, 5, 1);
//
//        when(workoutExerciseRepository.findById(1L)).thenReturn(Optional.of(workoutExercise));
//        when(userRepository.findUserByEmail(anyString())).thenReturn(user);
//        when(exerciseRepository.findById(any(Long.class))).thenReturn(Optional.of(newExercise));
//
//        workoutService.updateExercise("email", 1L, exerciseRequest);
//
//        verify(workoutExerciseRepository, times(1)).save(workoutExercise);
//        assertEquals(5, workoutExercise.getReps());
//        assertEquals(5, workoutExercise.getLoad());
//        assertEquals(newExercise, workoutExercise.getExerciseType());
//    }

    @Test
    @DisplayName("deleteExercise normal conditions")
    void testDeleteExercise() {
        User user = new User();
        Workout workout = new Workout();
        user.getWorkouts().add(workout);
        workout.setUser(user);

        WorkoutExercise workoutExercise1 = new WorkoutExercise();
        workoutExercise1.setSetNumber(1);
        workoutExercise1.setParentWorkout(workout);
        WorkoutExercise deletionExercise = new WorkoutExercise();
        deletionExercise.setSetNumber(1);
        deletionExercise.setParentWorkout(workout);
        WorkoutExercise workoutExercise3 = new WorkoutExercise();
        workoutExercise3.setSetNumber(2);
        workoutExercise3.setParentWorkout(workout);

        workout.setWorkoutExercises(new LinkedList<>(List.of(workoutExercise1, deletionExercise, workoutExercise3)));

        List<WorkoutExercise> mockExtract = new LinkedList<>(List.of(deletionExercise, workoutExercise3));
        try (MockedStatic<WorkoutService> mc = Mockito.mockStatic(WorkoutService.class)) {

            when(workoutExerciseRepository.findById(any(Long.class))).thenReturn(Optional.of(deletionExercise));
            when(userRepository.findUserByEmail(anyString())).thenReturn(user);
            mc.when(() ->
                            WorkoutService.extractExerciseSeries(workout.getWorkoutExercises(), deletionExercise))
                    .thenReturn(mockExtract);

            workoutService.deleteExercise("email", any(Long.class));
            assertFalse(mockExtract.contains(deletionExercise));
            verify(workoutExerciseRepository, times(1)).delete(deletionExercise);
            assertEquals(1, workoutExercise3.getSetNumber());
        }

    }

    @Test
    @DisplayName("extractExerciseSeries default test")
    void testExtractExerciseSeries() {
        Exercise latRaise = new Exercise();
        latRaise.setId(1L);
        latRaise.setName("Lat Raise");
        Exercise DumbbellCurl = new Exercise();
        DumbbellCurl.setId(2L);
        DumbbellCurl.setName("Dumbbell Curl");

        WorkoutExercise exercise1 = new WorkoutExercise();
        exercise1.setExerciseType(DumbbellCurl);
        WorkoutExercise exercise2 = new WorkoutExercise();
        exercise2.setExerciseType(latRaise);
        WorkoutExercise exercise3 = new WorkoutExercise();
        exercise3.setExerciseType(DumbbellCurl);
        WorkoutExercise exercise4 = new WorkoutExercise();
        exercise4.setExerciseType(latRaise);
        WorkoutExercise deletion = new WorkoutExercise();
        deletion.setExerciseType(latRaise);
        WorkoutExercise exercise6 = new WorkoutExercise();
        exercise6.setExerciseType(DumbbellCurl);
        WorkoutExercise exercise7 = new WorkoutExercise();
        exercise7.setExerciseType(latRaise);
        WorkoutExercise exercise8 = new WorkoutExercise();
        exercise8.setExerciseType(DumbbellCurl);

        List<WorkoutExercise> extracted =
                WorkoutService.extractExerciseSeries(
                        List.of(exercise1, exercise2, exercise3, exercise4, deletion, exercise6, exercise7, exercise8), deletion
                );
        assertEquals(2, extracted.size());
        assertTrue(extracted.contains(deletion));
        assertTrue(extracted.contains(exercise4));

    }
}