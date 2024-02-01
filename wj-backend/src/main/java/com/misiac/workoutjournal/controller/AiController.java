package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.requestmodels.AiPlanRequest;
import com.misiac.workoutjournal.service.AiService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    private final AiService aiService;

    @Autowired
    public AiController(AiService aiService) {
        this.aiService = aiService;
    }

    @GetMapping("/plan")
    public String getTrainingPlan(@RequestHeader(value = "Authorization") String token,
                                  @Valid @RequestBody AiPlanRequest aiPlanRequest) {
        return aiService.getTrainingPlan(aiPlanRequest);
    }
}