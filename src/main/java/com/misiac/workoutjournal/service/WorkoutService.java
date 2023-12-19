package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
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
    private final WorkoutMapper workoutMapper;


    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper, UserRepository userRepository) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
        this.userRepository = userRepository;
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
}
