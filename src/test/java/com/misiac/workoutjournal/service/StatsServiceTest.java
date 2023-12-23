package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    void getTotalSets() {
        User user = new User();
        WorkoutExercise workoutExercise = new WorkoutExercise();
        WorkoutExercise workoutExercise2 = new WorkoutExercise();

        workoutExercise.setLoad(5.5f);
        workoutExercise2.setLoad(5.5f);

        workoutExercise.setReps(2);
        workoutExercise2.setReps(4);

        Workout workout = new Workout();

        workout.setWorkoutExercises(List.of(workoutExercise,workoutExercise2));
        user.setWorkouts(Set.of(workout));

        when(userRepository.findUserByEmail("email")).thenReturn(user);
        Long result = statsService.getTotalSets("email");

        assertEquals(2,result);

    }
}