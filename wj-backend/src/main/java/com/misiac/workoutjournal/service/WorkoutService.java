package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.misiac.workoutjournal.repository.WorkoutRepository.WorkoutTiny;
import static com.misiac.workoutjournal.util.MessageProvider.*;

@Service
@Transactional
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;

    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.userRepository = userRepository;
    }


    public void addWorkout(Workout workout, String email) {

        if (workout.getId() >= 0) throw new IllegalArgumentException(NEW_ENTIY_ID);

        workout.getWorkoutExercises().forEach(exercise -> {
            if (exercise.getId() >= 0) throw new IllegalArgumentException(NEW_ENTIY_ID);

            exercise.getWorkoutExerciseSets().forEach(set -> {
                if (set.getId() >= 0) throw new IllegalArgumentException(NEW_ENTIY_ID);
            });
        });

        workout.setUser(userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST)));

        workoutRepository.save(workout);
    }

    public List<WorkoutTiny> getExercisesTiny(String email) {
        return workoutRepository.findByUserEmailOrderByDateDesc(email);
    }

    public Workout getSpecificWorkout(String email, Long id) {

        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST));
        if (!user.getWorkouts().contains(workout)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }
        return workout;
    }

    public void updateWorkout(String email, Workout modifiedWorkout) {

        Workout workoutFromDb = workoutRepository.findById(modifiedWorkout.getId()).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST));

        if (!user.getWorkouts().contains(workoutFromDb)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }

        modifiedWorkout.getWorkoutExercises().forEach(exercise -> {
            if (exercise.getId() > 0) {
                if (!workoutFromDb.getWorkoutExercises().contains(exercise)) {
                    throw new UnauthorizedException(EXERCISE_DOES_NOT_BELONG);
                }
            }
        });

        var listOfModifiedSets = modifiedWorkout.getWorkoutExercises().stream()
                .flatMap(workoutExercise -> workoutExercise.getWorkoutExerciseSets().stream())
                .filter(set -> set.getId() > 0)
                .toList();
        var listOfOriginalSets = workoutFromDb.getWorkoutExercises().stream()
                .flatMap(workoutExercise -> workoutExercise.getWorkoutExerciseSets().stream())
                .toList();

        listOfModifiedSets.forEach(modifiedSet -> {
            if (!listOfOriginalSets.contains(modifiedSet)) {
                throw new UnauthorizedException(SET_DOES_NOT_BELONG);
            }
        });

        workoutFromDb.setDate(modifiedWorkout.getDate());
        workoutFromDb.setName(modifiedWorkout.getName());

        workoutFromDb.getWorkoutExercises().clear();

        workoutFromDb.getWorkoutExercises().addAll(modifiedWorkout.getWorkoutExercises());

        workoutRepository.save(workoutFromDb);
    }

    public void deleteWorkout(String email, Long workoutId) {

        Workout deletion = workoutRepository.findById(workoutId).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST));

        if (!user.getWorkouts().contains(deletion)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }
        workoutRepository.delete(deletion);
    }


}