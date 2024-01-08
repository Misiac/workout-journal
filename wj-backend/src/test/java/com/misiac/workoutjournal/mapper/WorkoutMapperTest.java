package com.misiac.workoutjournal.mapper;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WorkoutMapperTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @InjectMocks
    private WorkoutMapper workoutMapper;

    @DisplayName("From WorkoutRequest to Workout class")
    @Test
    void toWorkout() {
        var date = LocalDateTime.now();
        int i = 1;
        Exercise exercise = new Exercise("Lat Raise");
        WorkoutRequest workoutRequest = new WorkoutRequest(date,
                List.of(
                        new ExerciseRequest(1L, 10F, 20, 1),
                        new ExerciseRequest(1L, 9F, 19, 2),
                        new ExerciseRequest(1L, 8F, 18, 3)));

        when(userRepository.findUserByEmail("test@email.com")).thenReturn(new User());
        when(exerciseRepository.findById(any())).thenReturn(Optional.of(exercise));

        Workout result = workoutMapper.toWorkout(workoutRequest, "test@email.com");

        assertEquals(date, result.getDate());
        for (var wExercise : result.getWorkoutExercises()) {

            assertEquals(i, wExercise.getSetNumber());
            assertEquals(21 - i, wExercise.getReps());
            assertEquals(11 - i, wExercise.getLoad());
            assertEquals(exercise, wExercise.getExerciseType());
            i++;
        }
    }
}