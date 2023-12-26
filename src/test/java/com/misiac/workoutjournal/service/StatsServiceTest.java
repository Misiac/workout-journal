package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class StatsServiceTest {
    @ExtendWith(MockitoExtension.class)

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("getTotalSets normal conditions method call")
    void testGetTotalSetsNormalConditions() {
        mockNormalConditionsUser();
        Long result = statsService.getTotalSets("email");

        assertEquals(2, result);
    }

    @Test
    @DisplayName("getTotalSets zero workouts")
    void testGetTotalSetsZeroWorkouts() {
        User user = new User();

        when(userRepository.findUserByEmail("email")).thenReturn(user);
        Long result = statsService.getTotalSets("email");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalVolume normal conditions method call")
    void testGetTotalVolumeNormalConditions() {
        mockNormalConditionsUser();
        Double result = statsService.getTotalVolume("email");

        assertEquals(33, result, 0.01);
    }

    @Test
    @DisplayName("getTotalVolume zero workouts")
    void testGetTotalVolumeZeroWorkouts() {
        User user = new User();

        when(userRepository.findUserByEmail("email")).thenReturn(user);
        Double result = statsService.getTotalVolume("email");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalWorkouts normal conditions method call")
    void testGetTotalWorkoutsNormalConditions() {
        mockNormalConditionsUser();

        Long result = statsService.getTotalWorkouts("email");

        assertEquals(1, result);
    }

    @Test
    @DisplayName("getTotalWorkouts zero workouts")
    void testGetTotalWorkoutsZeroWorkouts() {
        User user = new User();

        when(userRepository.findUserByEmail("email")).thenReturn(user);
        Long result = statsService.getTotalWorkouts("email");

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalReps normal conditions method call")
    void testGetTotalRepsNormalConditions() {
        mockNormalConditionsUser();
        Long result = statsService.getTotalReps("email");

        assertEquals(6, result);
    }

    @Test
    @DisplayName("getTotalReps normal zero workouts")
    void testGetTotalRepsZeroWorkouts() {
        User user = new User();

        when(userRepository.findUserByEmail("email")).thenReturn(user);

        Long result = statsService.getTotalReps("email");

        assertEquals(0, result);
    }


    private void mockNormalConditionsUser() {
        User user = new User();
        WorkoutExercise workoutExercise = new WorkoutExercise();
        WorkoutExercise workoutExercise2 = new WorkoutExercise();

        workoutExercise.setLoad(5.5f);
        workoutExercise2.setLoad(5.5f);

        workoutExercise.setReps(2);
        workoutExercise2.setReps(4);

        Workout workout = new Workout();

        workout.setWorkoutExercises(List.of(workoutExercise, workoutExercise2));
        user.setWorkouts(Set.of(workout));

        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);
    }
}