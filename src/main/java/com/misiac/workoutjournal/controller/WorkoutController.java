package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.WorkoutService;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
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
                              @RequestBody WorkoutRequest addWorkoutRequest) throws Exception {

        // authorize token, extract email TODO
        String email = "test@email.com";
        workoutService.addWorkout(addWorkoutRequest, email);

    }
}
