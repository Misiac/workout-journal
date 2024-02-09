package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.requestmodels.AiPlanRequest;
import com.misiac.workoutjournal.service.AiService;
import com.misiac.workoutjournal.util.JWTExtractor;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;
    private final JWTExtractor jwtExtractor;

    @Autowired
    public AiController(AiService aiService, JWTExtractor jwtExtractor) {
        this.aiService = aiService;
        this.jwtExtractor = jwtExtractor;
    }

    @PostMapping("/plan")
    public Map<String, String> getTrainingPlan(@RequestHeader(value = "Authorization") String token,
                                               @Valid @RequestBody AiPlanRequest aiPlanRequest) {
        return Map.of("generation", aiService.getTrainingPlan(aiPlanRequest));
    }

    @GetMapping("/analyze/workouts")
    public Map<String, String> analyzeWorkouts(@RequestHeader(value = "Authorization") String token) {
        String email = jwtExtractor.extractTokenParameter(token, JWTExtractor.ExtractionType.EMAIL);
        return Map.of("generation", aiService.analyzeUserWorkouts(email));
    }
}