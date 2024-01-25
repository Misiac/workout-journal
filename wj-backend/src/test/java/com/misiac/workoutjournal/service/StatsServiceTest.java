package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.entity.WorkoutExerciseSet;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.responsemodels.StatsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("getTotalSets normal conditions method call")
    void testGetTotalSetsNormalConditions() {
        var user = constructUser();
        Long result = statsService.getTotalSets(user);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("getTotalSets zero workouts")
    void testGetTotalSetsZeroWorkouts() {
        User user = new User();
        Long result = statsService.getTotalSets(user);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalVolume normal conditions method call")
    void testGetTotalVolumeNormalConditions() {
        var user = constructUser();
        Double result = statsService.getTotalVolume(user);

        assertEquals(33, result, 0.01);
    }

    @Test
    @DisplayName("getTotalVolume zero workouts")
    void testGetTotalVolumeZeroWorkouts() {
        User user = new User();
        Double result = statsService.getTotalVolume(user);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalWorkouts normal conditions method call")
    void testGetTotalWorkoutsNormalConditions() {
        var user = constructUser();

        Long result = statsService.getTotalWorkouts(user);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("getTotalWorkouts zero workouts")
    void testGetTotalWorkoutsZeroWorkouts() {
        User user = new User();

        Long result = statsService.getTotalWorkouts(user);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("getTotalReps normal conditions method call")
    void testGetTotalRepsNormalConditions() {
        var user = constructUser();
        Long result = statsService.getTotalReps(user);

        assertEquals(6, result);
    }

    @Test
    @DisplayName("getTotalReps normal zero workouts")
    void testGetTotalRepsZeroWorkouts() {
        User user = new User();

        Long result = statsService.getTotalReps(user);

        assertEquals(0, result);
    }

    @Test
    @DisplayName("GetTotalStats normal conditions")
    void testGetTotalStats() {

        var user = constructUser();
        Mockito.when(userRepository.findUserByEmail("email")).thenReturn(user);

        StatsDTO statsDTO = statsService.getTotalStats("email");

        assertEquals(6, statsDTO.getReps());
        assertEquals(1, statsDTO.getWorkouts());
        assertEquals(2, statsDTO.getSets());
        assertEquals(33, statsDTO.getVolume(), 0.01);
    }


    private User constructUser() {
        User user = new User();
        Workout workout = new Workout();
        WorkoutExercise we = new WorkoutExercise();
        WorkoutExerciseSet set1 = new WorkoutExerciseSet();
        WorkoutExerciseSet set2 = new WorkoutExerciseSet();

        set1.setLoad(5.5f);
        set2.setLoad(5.5f);

        set1.setReps(2);
        set2.setReps(4);

        we.setWorkoutExerciseSets(List.of(set1, set2));
        workout.setWorkoutExercises(List.of(we));
        user.setWorkouts(Set.of(workout));

        return user;
    }
}