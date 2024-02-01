package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.requestmodels.AiPlanRequest;
import com.misiac.workoutjournal.service.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    @Autowired
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @PostMapping("/plan")
    public Map<String, String> getTrainingPlan(@RequestHeader(value = "Authorization") String token,
                                               @Valid @RequestBody AiPlanRequest aiPlanRequest) {
        return Map.of("generation", aiService.getTrainingPlan(aiPlanRequest));
    }
}