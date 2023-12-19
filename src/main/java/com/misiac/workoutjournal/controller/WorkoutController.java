package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import com.misiac.workoutjournal.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/new")
    public void addNewWorkout(@RequestHeader(value = "Authorization") String token,
                              @RequestBody WorkoutRequest addWorkoutRequest) throws Exception {
        // authorize token, extract email TODO

        String email = "testuser@email.com";

        workoutService.addWorkout(addWorkoutRequest, email);
    }

    @GetMapping()
    public Page<Workout> getWorkoutsForUser(@RequestHeader(value = "Authorization") String token, Pageable pageable) {
        String email = "testuser@email.com";
        return workoutService.getWorkoutsForUser(email, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutId) throws Exception {
        String email = "testuser@email.com";
        workoutService.deleteWorkout(email, workoutId);
        return new ResponseEntity<>("xD", HttpStatus.OK);
    }

}
