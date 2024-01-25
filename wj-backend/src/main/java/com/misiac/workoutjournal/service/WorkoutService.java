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
import static com.misiac.workoutjournal.util.MessageProvider.WORKOUT_DOES_NOT_BELONG;
import static com.misiac.workoutjournal.util.MessageProvider.WORKOUT_DOES_NOT_EXIST;

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

//    public void addWorkout(WorkoutRequest addWorkoutRequest, String email) {

//    }

    public List<WorkoutTiny> getExercisesTiny(String email) {
        return workoutRepository.findByUserEmailOrderByDateDesc(email);
    }

    public Workout getSpecificWorkout(String email, Long id) {

        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (!user.getWorkouts().contains(workout)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }
        return workout;
    }

    public void updateWorkout(String email, Workout modifiedWorkout) {

        Workout workoutFromDb = workoutRepository.findById(modifiedWorkout.getId()).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (!user.getWorkouts().contains(workoutFromDb)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }

        workoutFromDb.setDate(modifiedWorkout.getDate());
        workoutFromDb.setName(modifiedWorkout.getName());
        workoutFromDb.setWorkoutExercises(modifiedWorkout.getWorkoutExercises());

        workoutRepository.save(workoutFromDb);

    }

    public void deleteWorkout(String email, Long workoutId) {

        Workout deletion = workoutRepository.findById(workoutId).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (!user.getWorkouts().contains(deletion)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }
        workoutRepository.delete(deletion);
    }
}