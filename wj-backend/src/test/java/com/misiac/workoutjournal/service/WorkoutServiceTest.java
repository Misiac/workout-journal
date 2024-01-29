package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    WorkoutService workoutService;


    @Test
    @DisplayName("deleteWorkout normal conditions")
    void testDeleteWorkout() {
        Workout workout = new Workout();
        User user = new User();
        user.getWorkouts().add(workout);
        Long workoutId = 5L;
        String email = "test@email.com";

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user));

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
        when(userRepository.findUserByEmail(email)).thenReturn(Optional.of(user2));

        assertThrows(UnauthorizedException.class,
                () -> workoutService.deleteWorkout(email, workoutId)
        );
        verify(workoutRepository, times(0)).delete(workout);
    }
}