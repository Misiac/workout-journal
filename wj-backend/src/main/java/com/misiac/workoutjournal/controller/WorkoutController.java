package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.service.WorkoutService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.misiac.workoutjournal.repository.WorkoutRepository.WorkoutTiny;
import static com.misiac.workoutjournal.util.MessageProvider.*;

@RestController
@RequestMapping("/api/workouts")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final JWTExtractor jwtExtractor;

    @Autowired
    public WorkoutController(WorkoutService workoutService, JWTExtractor jwtExtractor) {
        this.workoutService = workoutService;
        this.jwtExtractor = jwtExtractor;
    }

//    @PostMapping("")
//    public ResponseEntity<String> addNewWorkout(@RequestHeader(value = "Authorization") String token,
//                                                @RequestBody WorkoutRequest addWorkoutRequest) {
//        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
//        workoutService.addWorkout(addWorkoutRequest, email);
//        return new ResponseEntity<>(WORKOUT_CREATED, HttpStatus.CREATED);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutId) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.deleteWorkout(email, workoutId);
        return new ResponseEntity<>(WORKOUT_DELETED, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<String> updateWorkout(
            @RequestHeader(value = "Authorization") String token, @RequestBody Workout workout) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.updateWorkout(email, workout);
        return new ResponseEntity<>(WORKOUT_UPDATED, HttpStatus.OK);
    }

    @GetMapping("/tiny")
    public List<WorkoutTiny> getWorkoutsTiny(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return workoutService.getExercisesTiny(email);
    }

    @GetMapping("/{id}")
    public Workout getSpecificWorkout(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long id) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return workoutService.getSpecificWorkout(email, id);
    }
}