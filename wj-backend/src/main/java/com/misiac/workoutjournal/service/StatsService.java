package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.*;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
import com.misiac.workoutjournal.responsemodels.StatsDTO;
import com.misiac.workoutjournal.responsemodels.TotalsDTO;
import com.misiac.workoutjournal.util.RadarAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.misiac.workoutjournal.util.MessageProvider.USER_DOES_NOT_EXIST;

@Service
public class StatsService {

    private final UserRepository userRepository;
    private final RadarAllocator radarAllocator;
    private final UserService userService;

    @Autowired
    public StatsService(UserRepository userRepository, RadarAllocator radarAllocator, UserService userService) {
        this.userRepository = userRepository;
        this.radarAllocator = radarAllocator;
        this.userService = userService;
    }


    public Long getTotalReps(User user) {

        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .flatMap(workoutExercise -> workoutExercise.getWorkoutExerciseSets().stream())
                .mapToLong(WorkoutExerciseSet::getReps)
                .sum();
    }

    public Long getTotalWorkouts(User user) {
        return (long) user.getWorkouts().size();
    }

    public Long getTotalSets(User user) {

        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .mapToLong(workoutExercise -> workoutExercise.getWorkoutExerciseSets().size())
                .sum();
    }

    public Double getTotalVolume(User user) {

        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .flatMap(workoutExercise -> workoutExercise.getWorkoutExerciseSets().stream())
                .mapToDouble(we -> we.getLoad() * we.getReps())
                .sum();
    }

    public RadarDataDTO getRadarData(User user) {

        RadarDataDTO radarDataDTO = new RadarDataDTO();
        for (Workout workout : user.getWorkouts()) {
            for (WorkoutExercise we : workout.getWorkoutExercises()) {
                for (WorkoutExerciseSet set : we.getWorkoutExerciseSets()) {
                    for (MuscleGroup mg : we.getExerciseType().getMuscleGroups()) {
                        if (mg.getIsPrimary() == 1) {
                            radarAllocator.incrementRadarCategory(radarDataDTO, mg.getCategory(), set.getReps());
                        }
                    }
                }
            }
        }
        return radarDataDTO;
    }

    public TotalsDTO getTotalStats(User user) {


        if (user.getWorkouts().isEmpty()) {
            return new TotalsDTO(0L, 0D, 0L, 0L);
        }

        return new TotalsDTO(
                getTotalReps(user),
                getTotalVolume(user),
                getTotalSets(user),
                getTotalWorkouts(user)
        );
    }

    public StatsDTO getStatsData(String email) {
        userService.createUserIfNotExists(email);

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST));

        return new StatsDTO(
                getTotalStats(user),
                getRadarData(user)
        );
    }
}
