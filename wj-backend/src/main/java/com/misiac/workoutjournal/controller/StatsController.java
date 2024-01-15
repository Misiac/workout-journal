package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
import com.misiac.workoutjournal.service.StatsService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.misiac.workoutjournal.util.JWTExtractor.ExtractionType;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final StatsService statsService;
    private final JWTExtractor jwtExtractor;

    @Autowired
    public StatsController(StatsService statsService, JWTExtractor jwtExtractor) {
        this.statsService = statsService;
        this.jwtExtractor = jwtExtractor;
    }

    @GetMapping("/total/reps")
    public Long getTotalReps(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalReps(email);
    }

    @GetMapping("/total/workouts")
    public Long getTotalWorkouts(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalWorkouts(email);
    }

    @GetMapping("/total/sets")
    public Long getTotalSets(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalSets(email);
    }

    @GetMapping("/total/volume")
    public Double getTotalVolume(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getTotalVolume(email);
    }

    @GetMapping("/radar")
    public RadarDataDTO getRadarData(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, ExtractionType.EMAIL);
        return statsService.getRadarData(email);
    }
}
