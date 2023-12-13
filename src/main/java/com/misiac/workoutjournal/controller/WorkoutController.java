package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.WorkoutService;
import com.misiac.workoutjournal.requestmodels.AddWorkoutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workout")
//@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;

    @Autowired
    public WorkoutController(WorkoutService workoutService) {
        this.workoutService = workoutService;
    }

    @PostMapping()
    public void addNewWorkout(@RequestHeader(value = "Authorization") String token,
                              @RequestBody AddWorkoutRequest addWorkoutRequest) throws Exception {


    }
}
