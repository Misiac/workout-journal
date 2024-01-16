package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.responsemodels.StatsDTO;
import com.misiac.workoutjournal.util.RadarAllocator;
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
    @Mock
    private RadarAllocator radarAllocator;

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
    @DisplayName("GetRadarData normal conditions")
    void testGetRadarData() {

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
        WorkoutExercise workoutExercise = new WorkoutExercise();
        WorkoutExercise workoutExercise2 = new WorkoutExercise();

        workoutExercise.setLoad(5.5f);
        workoutExercise2.setLoad(5.5f);

        workoutExercise.setReps(2);
        workoutExercise2.setReps(4);

        Workout workout = new Workout();

        workout.setWorkoutExercises(List.of(workoutExercise, workoutExercise2));
        user.setWorkouts(Set.of(workout));

        return user;
    }
}