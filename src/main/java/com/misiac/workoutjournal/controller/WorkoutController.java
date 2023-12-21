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

import static com.misiac.workoutjournal.util.JWTExtractor.extractTokenParameter;

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
    public ResponseEntity<String> addNewWorkout(@RequestHeader(value = "Authorization") String token,
                                                @RequestBody WorkoutRequest addWorkoutRequest) throws Exception {
        String email = extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.addWorkout(addWorkoutRequest, email);
        return new ResponseEntity<>("Workout created successfully", HttpStatus.CREATED);
    }

    @GetMapping()
    public Page<Workout> getWorkoutsForUser(@RequestHeader(value = "Authorization") String token, Pageable pageable) {
        String email = extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return workoutService.getWorkoutsForUser(email, pageable);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkout(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutId) throws Exception {
        String email = extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.deleteWorkout(email, workoutId);
        return new ResponseEntity<>("Workout deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity<String> updateExercise(
            @RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutExerciseId,
            @RequestBody ExerciseRequest exerciseRequest) throws Exception {
        String email = extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.updateExercise(email, workoutExerciseId, exerciseRequest);
        return new ResponseEntity<>("Exercise updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<String> deleteExercise(@RequestHeader(value = "Authorization") String token, @PathVariable(name = "id") Long workoutExerciseId) throws Exception {
        String email = extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        workoutService.deleteExercise(email, workoutExerciseId);
        return new ResponseEntity<>("Exercise deleted successfully", HttpStatus.OK);

    }
}
