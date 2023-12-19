package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    private final UserRepository userRepository;

    @Autowired
    public StatsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Long getTotalReps(String email) {
        User user = userRepository.findUserByEmail(email);
        long total = 0;

        for (Workout workout : user.getWorkouts()) {
            for (WorkoutExercise workoutExercise : workout.getWorkoutExercises()) {
                total += workoutExercise.getReps();
            }
        }
        return total;
    }
}
