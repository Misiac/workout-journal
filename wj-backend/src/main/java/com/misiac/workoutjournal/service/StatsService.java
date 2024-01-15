package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.*;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
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


    public Long getTotalReps(String email) {
        User user = userRepository.findUserByEmail(email);

        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .mapToLong(WorkoutExercise::getReps)
                .sum();
    }

    public Long getTotalWorkouts(String email) {
        User user = userRepository.findUserByEmail(email);
        return (long) user.getWorkouts().size();
    }

    public Long getTotalSets(String email) {
        User user = userRepository.findUserByEmail(email);

        return user.getWorkouts().stream()
                .mapToLong(workout -> workout.getWorkoutExercises().size())
                .sum();
    }

    public Double getTotalVolume(String email) {

        User user = userRepository.findUserByEmail(email);
        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .mapToDouble(we -> we.getLoad() * we.getReps())
                .sum();
    }

    public RadarDataDTO getRadarData(String email) {
        User user = userRepository.findUserByEmail(email);
        RadarDataDTO radarDataDTO = new RadarDataDTO();
        for (Workout workout : user.getWorkouts()) {
            for (WorkoutExercise we : workout.getWorkoutExercises()) {
                for (MuscleGroup mg : we.getExerciseType().getMuscleGroups()) {
                    if (mg.getIsPrimary() == 1) {
                        radarAllocator.incrementRadarCategory(radarDataDTO, mg.getCategory(), we.getReps());
                    }
                }
            }
        }
        return radarDataDTO;
    }
}
