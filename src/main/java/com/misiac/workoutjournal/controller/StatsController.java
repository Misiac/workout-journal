package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.misiac.workoutjournal.util.JWTExtractor.ExtractionType;
import static com.misiac.workoutjournal.util.JWTExtractor.extractTokenParameter;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/total/reps")
    public Long getTotalReps(@RequestHeader(value = "Authorization") String token) {
        String email = extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalReps(email);
    }

    @GetMapping("/total/workouts")
    public Long getTotalWorkouts(@RequestHeader(value = "Authorization") String token) {
        String email = extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalWorkouts(email);
    }

    @GetMapping("/total/sets")
    public Long getTotalSets(@RequestHeader(value = "Authorization") String token) {
        String email = extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalSets(email);
    }

    @GetMapping("/total/volume")
    public Double getTotalVolume(@RequestHeader(value = "Authorization") String token) {
        String email = extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalVolume(email);
    }


}
