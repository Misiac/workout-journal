package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExerciseSet;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutExerciseSetRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
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
    private final WorkoutExerciseSetRepository workoutExerciseSetRepository;
    private final WorkoutMapper workoutMapper;


    @Autowired
    public WorkoutService(
            WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository,
            WorkoutExerciseSetRepository workoutExerciseSetRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.userRepository = userRepository;
        this.workoutExerciseSetRepository = workoutExerciseSetRepository;
    }


//    public void addWorkout(WorkoutRequest addWorkoutRequest, String email) {
//
//        Workout workout = workoutMapper.toWorkout(addWorkoutRequest, email);
//        workoutRepository.save(workout);
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

    public void deleteWorkout(String email, Long workoutId) {

        Workout deletion = workoutRepository.findById(workoutId).orElseThrow(
                () -> new EntityDoesNotExistException(WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (!user.getWorkouts().contains(deletion)) {
            throw new UnauthorizedException(WORKOUT_DOES_NOT_BELONG);
        }
        workoutRepository.delete(deletion);
    }

    public void updateExercises(String email, List<ExerciseRequest> exerciseRequests) {

        exerciseRequests.forEach(request -> {
                    WorkoutExerciseSet workoutExerciseSet = workoutExerciseSetRepository.findById(request.getId())
                            .orElseThrow(() -> new EntityDoesNotExistException(SET_DOES_NOT_EXIST));

                    User user = userRepository.findUserByEmail(email);
                    if (workoutExerciseSet.getParentWorkoutExercise().getParentWorkout().getUser() != user) {
                        throw new UnauthorizedException(SET_DOES_NOT_BELONG);
                    }

                    workoutExerciseSet.setLoad(request.getLoad());
                    workoutExerciseSet.setReps(request.getReps());

                    workoutExerciseSetRepository.save(workoutExerciseSet);
                }
        );
    }

    public void deleteExercises(String email, List<Long> deleteIds) {

        User user = userRepository.findUserByEmail(email);
        deleteIds.forEach(id -> {

            WorkoutExerciseSet deletion = workoutExerciseSetRepository.findById(id)
                    .orElseThrow(() -> new EntityDoesNotExistException(SET_DOES_NOT_EXIST));

            if (deletion.getParentWorkoutExercise().getParentWorkout().getUser() != user) {
                throw new UnauthorizedException(SET_DOES_NOT_BELONG);
            }
            workoutExerciseSetRepository.delete(deletion);
        });
    }
}
