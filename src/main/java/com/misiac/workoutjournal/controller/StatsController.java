package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/total-reps")
    public Long getTotalReps(@RequestHeader(value = "Authorization") String token) {
        String email = "testuser@email.com";
        return statsService.getTotalReps(email);
    }

    @GetMapping("/total/workouts")
    public Long getTotalWorkouts(@RequestHeader(value = "Authorization") String token) {
        String email = "testuser@email.com";
        return statsService.getTotalWorkouts(email);
    }

    @GetMapping("/total/sets")
    public Long getTotalSets(@RequestHeader(value = "Authorization") String token) {
        String email = "testuser@email.com";
        return statsService.getTotalSets(email);
    }

}
