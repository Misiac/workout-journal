package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.responsemodels.ExerciseTinyDTO;
import com.misiac.workoutjournal.service.ExerciseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {
    private final ExerciseService exerciseService;


    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }
    @GetMapping("/tiny")
    public List<ExerciseTinyDTO> getAllExercisesTiny() {
        return exerciseService.getAllExercisesTiny();
    }

}
