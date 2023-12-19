package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
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

import java.util.Optional;

@Service
@Transactional
public class WorkoutService {

    private final WorkoutRepository workoutRepository;
    private final UserRepository userRepository;
    private final WorkoutExerciseRepository workoutExerciseRepository;
    private final ExerciseRepository exerciseRepository;
    private final WorkoutMapper workoutMapper;


    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository, WorkoutExerciseRepository workoutExerciseRepository, ExerciseRepository exerciseRepository) {
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

    public void deleteWorkout(String email, Long workoutId) throws Exception {

        Optional<Workout> deletionOptional = workoutRepository.findById(workoutId);
        if (deletionOptional.isEmpty()) {
            throw new Exception("Workout does not exist");
        }
        Workout deletion = deletionOptional.get();
        User user = userRepository.findUserByEmail(email);
        if (!user.getWorkouts().contains(deletion)) {
            throw new Exception("This workout does not belong to this user");
        }
        workoutRepository.delete(deletion);
    }

    public void updateExercise(String email, Long workoutExerciseId, ExerciseRequest exerciseRequest) throws Exception {

        Optional<WorkoutExercise> workoutExerciseOpt = workoutExerciseRepository.findById(workoutExerciseId);

        if (workoutExerciseOpt.isEmpty()) {
            throw new Exception("Workout exercise does not exist");
        }
        WorkoutExercise workoutExercise = workoutExerciseOpt.get();
        User user = userRepository.findUserByEmail(email);
        if (workoutExercise.getParentWorkout().getUser() != user) {
            throw new Exception("This workout exercise does not belong to this user");
        }
        if (exerciseRequest.getExerciseId() != workoutExercise.getExerciseType().getId()) {
            Optional<Exercise> newExercise = exerciseRepository.findById(exerciseRequest.getExerciseId());
            if (newExercise.isEmpty()) throw new Exception("Exercise does not exist");
            workoutExercise.setExerciseType(newExercise.get());
        }
        workoutExercise.setLoad(exerciseRequest.getLoad());
        workoutExercise.setReps(exerciseRequest.getReps());

        workoutExerciseRepository.save(workoutExercise);

    }
}
