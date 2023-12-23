package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.exception.EntityDoesNotExistException;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutExerciseRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.misiac.workoutjournal.util.MessageProvider.*;

@Service
@Transactional
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutMapper workoutMapper;


    @Autowired
    public WorkoutService(
            WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository,
            WorkoutExerciseRepository workoutExerciseRepository, ExerciseRepository exerciseRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.userRepository = userRepository;
        this.workoutExerciseRepository = workoutExerciseRepository;
        this.exerciseRepository = exerciseRepository;
    }


    public void addWorkout(WorkoutRequest addWorkoutRequest, String email) {

        Workout workout = workoutMapper.toWorkout(addWorkoutRequest, email);
        workoutRepository.save(workout);
    }

    public Page<Workout> getWorkoutsForUser(String email, Pageable pageable) {
        return workoutRepository.findWorkoutsByUserEmailOrderByDateDesc(email, pageable);

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

    public void updateExercise(String email, Long workoutExerciseId, ExerciseRequest exerciseRequest) {

        WorkoutExercise workoutExercise = workoutExerciseRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new EntityDoesNotExistException(WORKOUT_EXERCISE_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (workoutExercise.getParentWorkout().getUser() != user) {
            throw new UnauthorizedException(WE_DOES_NOT_BELONG);
        }
        if (exerciseRequest.getExerciseId() != workoutExercise.getExerciseType().getId()) {

            Exercise newExercise = exerciseRepository.findById(exerciseRequest.getExerciseId())
                    .orElseThrow(() -> new EntityDoesNotExistException(EXERCISE_DOES_NOT_EXIST));
            workoutExercise.setExerciseType(newExercise);
        }
        workoutExercise.setLoad(exerciseRequest.getLoad());
        workoutExercise.setReps(exerciseRequest.getReps());

        workoutExerciseRepository.save(workoutExercise);
    }

    public void deleteExercise(String email, Long workoutExerciseId) {

        WorkoutExercise deletion = workoutExerciseRepository.findById(workoutExerciseId)
                .orElseThrow(() -> new EntityDoesNotExistException(WORKOUT_EXERCISE_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email);
        if (deletion.getParentWorkout().getUser() != user) {
            throw new UnauthorizedException(WE_DOES_NOT_BELONG);
        }
        Workout workout = deletion.getParentWorkout();
        List<WorkoutExercise> workoutExercises = workout.getWorkoutExercises();
        List<WorkoutExercise> exercisesSeries;

        exercisesSeries = extractExerciseSeries(workoutExercises, deletion);
        exercisesSeries.remove(deletion);
        workoutExerciseRepository.delete(deletion);
        for (int i = 1; i <= exercisesSeries.size(); i++) {
            exercisesSeries.get(i - 1).setSetNumber(i);
            workoutExerciseRepository.save(exercisesSeries.get(i - 1));
        }
    }

    //This method extract a sublist of exercises which contain a given workout exercise and is surrounded by the exercises of the same type
    // it mainly serves for deletion => when deleting an exercise you have to change all set numbers down
    private static List<WorkoutExercise> extractExerciseSeries(List<WorkoutExercise> workoutExercises, WorkoutExercise deletionExercise) {

        int firstIndex = -1;
        List<WorkoutExercise> exercisesSeries = null;
        for (int i = 0; i < workoutExercises.size(); i++) {
            var exercise = workoutExercises.get(i);
            if (exercise.getExerciseType() == deletionExercise.getExerciseType()) {
                if (firstIndex == -1) {
                    firstIndex = i;
                }
                WorkoutExercise nextExercise;
                try {
                    nextExercise = workoutExercises.get(i + 1);
                } catch (IndexOutOfBoundsException e) {
                    exercisesSeries = workoutExercises.subList(firstIndex, i + 1);
                    break;
                }

                if (nextExercise.getExerciseType() != deletionExercise.getExerciseType()) {
                    exercisesSeries = workoutExercises.subList(firstIndex, i + 1);
                    if (exercisesSeries.contains(deletionExercise)) break;
                    else firstIndex = -1;
                }
            }
        }
        return exercisesSeries;
    }
}
