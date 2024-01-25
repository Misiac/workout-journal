package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.*;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
import com.misiac.workoutjournal.responsemodels.StatsDTO;
import com.misiac.workoutjournal.util.RadarAllocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final UserRepository userRepository;
    private final RadarAllocator radarAllocator;

    @Autowired
    public StatsService(UserRepository userRepository, RadarAllocator radarAllocator) {
        this.userRepository = userRepository;
        this.radarAllocator = radarAllocator;
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

    public RadarDataDTO getRadarData(String email) {
        User user = userRepository.findUserByEmail(email);
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

    public StatsDTO getTotalStats(String email) {

        User user = userRepository.findUserByEmail(email);

        return new StatsDTO(
                getTotalReps(user),
                getTotalVolume(user),
                getTotalSets(user),
                getTotalWorkouts(user)
        );
    }
}
