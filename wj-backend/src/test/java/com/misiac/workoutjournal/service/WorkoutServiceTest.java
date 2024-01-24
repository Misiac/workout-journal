package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutExerciseRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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


    @Test
    @DisplayName("Update WorkoutExercise from List<ExerciseRequest>")
    void testUpdateExercises() {
        User user = new User();
        Workout workout = new Workout();
        workout.setUser(user);
        Exercise exercise = new Exercise(1L, "Lat Raise", null, null);

        WorkoutExercise we1 = new WorkoutExercise(1L, workout, exercise, 2F, 2, 1);
        WorkoutExercise we2 = new WorkoutExercise(2L, workout, exercise, 2F, 2, 2);

        ExerciseRequest request1 = new ExerciseRequest(1L, 5F, 5, 1);
        ExerciseRequest request2 = new ExerciseRequest(2L, 6F, 6, 2);

        when(workoutExerciseRepository.findById(1L)).thenReturn(Optional.of(we1));
        when(workoutExerciseRepository.findById(2L)).thenReturn(Optional.of(we2));

        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        workoutService.updateExercises("email", List.of(request1, request2));

        verify(workoutExerciseRepository, times(1)).save(we1);
        verify(workoutExerciseRepository, times(1)).save(we2);

        assertEquals(5, we1.getReps());
        assertEquals(5, we1.getLoad(), 0.01);

        assertEquals(6, we2.getReps());
        assertEquals(6, we2.getLoad(), 0.01);
    }

    @Test
    @DisplayName("deleteExercise normal conditions")
    void testDeleteExercise() {
        User user = new User();
        Workout workout = new Workout();
        user.getWorkouts().add(workout);
        workout.setUser(user);

        WorkoutExercise we1 = new WorkoutExercise();
        we1.setId(1L);
        we1.setParentWorkout(workout);

        WorkoutExercise we2 = new WorkoutExercise();
        we2.setId(2L);
        we2.setParentWorkout(workout);

        WorkoutExercise we3 = new WorkoutExercise();
        we3.setId(3L);
        we3.setParentWorkout(workout);


        workout.setWorkoutExercises(new LinkedList<>(List.of(we1, we2)));

        when(userRepository.findUserByEmail(anyString())).thenReturn(user);

        when(workoutExerciseRepository.findById(1L)).thenReturn(Optional.of(we1));
        when(workoutExerciseRepository.findById(2L)).thenReturn(Optional.of(we2));

        workoutService.deleteExercises("email", List.of(1L, 2L));

        verify(workoutExerciseRepository, times(1)).delete(we1);
        verify(workoutExerciseRepository, times(1)).delete(we2);
        verify(workoutExerciseRepository, times(0)).delete(we3);

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