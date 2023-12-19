package com.misiac.workoutjournal.mapper;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("OptionalGetWithoutIsPresent")
@Component
public class WorkoutMapper {
    private final ExerciseRepository exerciseRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkoutMapper(ExerciseRepository exerciseRepository, UserRepository userRepository) {
        this.exerciseRepository = exerciseRepository;
        this.userRepository = userRepository;
    }

    public Workout toWorkout(WorkoutRequest workoutRequest, String email) {

        Workout workout = new Workout();
        List<WorkoutExercise> workoutExercises = new LinkedList<>();

        workout.setDate(workoutRequest.getDate());
        workout.setUser(
                userRepository.findUserByEmail(email)
        );

        for (ExerciseRequest exerciseRequest : workoutRequest.getExercises()) {

            WorkoutExercise workoutExercise = new WorkoutExercise();
            workoutExercise.setExerciseType(
                    exerciseRepository.findById(exerciseRequest.getExerciseId()).get());
            workoutExercise.setLoad(exerciseRequest.getLoad());
            workoutExercise.setReps(exerciseRequest.getReps());
            workoutExercise.setSetNumber(exerciseRequest.getSetNumber());
            workoutExercise.setParentWorkout(workout);
            workoutExercises.add(workoutExercise);
        }
        workout.setWorkoutExercises(workoutExercises);

        return workout;
    }
}
