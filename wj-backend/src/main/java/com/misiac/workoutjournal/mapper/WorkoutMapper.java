package com.misiac.workoutjournal.mapper;

import com.misiac.workoutjournal.repository.ExerciseTypeRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkoutMapper {
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkoutMapper(ExerciseTypeRepository exerciseTypeRepository, UserRepository userRepository) {
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.userRepository = userRepository;
    }

//    public Workout toWorkout(WorkoutRequest workoutRequest, String email) {
//
//        Workout workout = new Workout();
//        List<WorkoutExercise> workoutExercises = new LinkedList<>();
//
//        workout.setDate(workoutRequest.getDate());
//        workout.setUser(
//                userRepository.findUserByEmail(email)
//        );
//
//        for (ExerciseRequest exerciseRequest : workoutRequest.getExercises()) {
//
//            WorkoutExercise workoutExercise = new WorkoutExercise();
//            workoutExercise.setExerciseType(
//                    exerciseTypeRepository.findById(exerciseRequest.getId()).orElseThrow());
//            workoutExercise.setLoad(exerciseRequest.getLoad());
//            workoutExercise.setReps(exerciseRequest.getReps());
//            workoutExercise.setSetNumber(exerciseRequest.getSetNumber());
//            workoutExercise.setParentWorkout(workout);
//            workoutExercises.add(workoutExercise);
//        }
//        workout.setWorkoutExercises(workoutExercises);
//
//        return workout;
//    }
}
