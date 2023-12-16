package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;


    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }


    public void addWorkout(WorkoutRequest addWorkoutRequest, String email) {

        Workout workout = new Workout();

        workout.setDate(addWorkoutRequest.getDate());
        workout.setUser(
                userRepository.findUserByEmail(email)
        );

        for (ExerciseRequest exerciseRequest : addWorkoutRequest.getExercises()) {

            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setWorkout(workout);
            workoutExercise.setExercise(
                    exerciseRepository.findExerciseById(exerciseRequest.getExerciseId()));
            workoutExercise.setLoad(exerciseRequest.getLoad());
            workoutExercise.setReps(exerciseRequest.getReps());
            workoutExercise.setSetNumber(exerciseRequest.getSetNumber());
        }
        workoutRepository.save(workout);

    }
}
