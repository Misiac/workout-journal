package ovh.workoutjournal.service;

import ovh.workoutjournal.entity.User;
import ovh.workoutjournal.entity.Workout;
import ovh.workoutjournal.exception.EntityDoesNotExistException;
import ovh.workoutjournal.exception.UnauthorizedException;
import ovh.workoutjournal.repository.UserRepository;
import ovh.workoutjournal.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ovh.workoutjournal.util.MessageProvider;

import java.util.List;

import static ovh.workoutjournal.repository.WorkoutRepository.WorkoutTiny;

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

        if (workout.getId() >= 0) throw new IllegalArgumentException(MessageProvider.NEW_ENTIY_ID);

        workout.getWorkoutExercises().forEach(exercise -> {
            if (exercise.getId() >= 0) throw new IllegalArgumentException(MessageProvider.NEW_ENTIY_ID);

            exercise.getWorkoutExerciseSets().forEach(set -> {
                if (set.getId() >= 0) throw new IllegalArgumentException(MessageProvider.NEW_ENTIY_ID);
            });
        });

        workout.setUser(userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.USER_DOES_NOT_EXIST)));

        workoutRepository.save(workout);
    }

    public List<WorkoutTiny> getExercisesTiny(String email) {
        return workoutRepository.findByUserEmailOrderByDateDesc(email);
    }

    public Workout getSpecificWorkout(String email, Long id) {

        Workout workout = workoutRepository.findById(id).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.USER_DOES_NOT_EXIST));
        if (!user.getWorkouts().contains(workout)) {
            throw new UnauthorizedException(MessageProvider.WORKOUT_DOES_NOT_BELONG);
        }
        return workout;
    }

    public void updateWorkout(String email, Workout modifiedWorkout) {

        Workout workoutFromDb = workoutRepository.findById(modifiedWorkout.getId()).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.USER_DOES_NOT_EXIST));

        if (!user.getWorkouts().contains(workoutFromDb)) {
            throw new UnauthorizedException(MessageProvider.WORKOUT_DOES_NOT_BELONG);
        }

        modifiedWorkout.getWorkoutExercises().forEach(exercise -> {
            if (exercise.getId() > 0) {
                if (!workoutFromDb.getWorkoutExercises().contains(exercise)) {
                    throw new UnauthorizedException(MessageProvider.EXERCISE_DOES_NOT_BELONG);
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
                throw new UnauthorizedException(MessageProvider.SET_DOES_NOT_BELONG);
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
                () -> new EntityDoesNotExistException(MessageProvider.WORKOUT_DOES_NOT_EXIST));

        User user = userRepository.findUserByEmail(email).orElseThrow(
                () -> new EntityDoesNotExistException(MessageProvider.USER_DOES_NOT_EXIST));

        if (!user.getWorkouts().contains(deletion)) {
            throw new UnauthorizedException(MessageProvider.WORKOUT_DOES_NOT_BELONG);
        }
        workoutRepository.delete(deletion);
    }


}