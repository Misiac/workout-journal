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

        return user.getWorkouts().stream()
                .flatMap(workout -> workout.getWorkoutExercises().stream())
                .mapToLong(WorkoutExercise::getReps)
                .sum();
    }

    public Long getTotalWorkouts(String email) {
        User user = userRepository.findUserByEmail(email);
        return (long) user.getWorkouts().size();
    }

}
