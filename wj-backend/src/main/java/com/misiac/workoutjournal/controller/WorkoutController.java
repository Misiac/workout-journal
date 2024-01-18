package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import com.misiac.workoutjournal.service.WorkoutService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.misiac.workoutjournal.repository.WorkoutRepository.WorkoutTiny;
import static com.misiac.workoutjournal.util.MessageProvider.*;

@RestController
@RequestMapping("/api/workout")
public class WorkoutController {

    private final WorkoutService workoutService;
    private final JWTExtractor jwtExtractor;

    @Autowired
    public WorkoutController(WorkoutService workoutService, JWTExtractor jwtExtractor) {
        this.workoutService = workoutService;
        this.jwtExtractor = jwtExtractor;
    }

    @PostMapping("/new")
    public ResponseEntity<String> addNewWorkout(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody WorkoutRequest addWorkoutRequest) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.addWorkout(addWorkoutRequest, email);
        return new ResponseEntity<>(WORKOUT_CREATED, HttpStatus.CREATED);
    }

    @GetMapping()
    public Page<Workout> getWorkoutsForUser(@RequestHeader(value = "Authorization") String token, Pageable pageable) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return workoutService.getWorkoutsForUser(email, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutId) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.deleteWorkout(email, workoutId);
        return new ResponseEntity<>(WORKOUT_DELETED, HttpStatus.OK);
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity<String> updateExercise(
            @RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutExerciseId,
            @RequestBody ExerciseRequest exerciseRequest) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.updateExercise(email, workoutExerciseId, exerciseRequest);
        return new ResponseEntity<>(EXERCISE_UPDATED, HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<String> deleteExercise(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutExerciseId) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.deleteExercise(email, workoutExerciseId);
        return new ResponseEntity<>(EXERCISE_DELETED, HttpStatus.OK);
    }

    @GetMapping("/tiny")
    public List<WorkoutTiny> getWorkoutsTiny(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return workoutService.getExercisesTiny(email);

    }
}
