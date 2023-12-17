package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.mapper.WorkoutMapper;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final WorkoutMapper workoutMapper;


    @Autowired
    public WorkoutService(WorkoutRepository workoutRepository, WorkoutMapper workoutMapper) {
        this.workoutRepository = workoutRepository;
        this.workoutMapper = workoutMapper;
    }


    public void addWorkout(WorkoutRequest addWorkoutRequest, String email) {

        Workout workout = workoutMapper.toWorkout(addWorkoutRequest, email);
        workoutRepository.save(workout);
    }

    public List<Workout> getWorkouts(String email) {
        return workoutRepository.findWorkoutsByUserEmail(email);

    }
}
