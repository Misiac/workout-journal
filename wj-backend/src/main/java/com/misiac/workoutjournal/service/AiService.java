package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.requestmodels.AiPlanRequest;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    private final ChatClient chatClient;

    @Autowired
    public AiService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String getTrainingPlan(AiPlanRequest aiPlanRequest) {

        return chatClient.call(TrainingPlanPromptFormatter.createTrainingPlanPrompt(aiPlanRequest));
    }
}