package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.requestmodels.AiPlanRequest;

public class TrainingPlanPromptFormatter {
    private TrainingPlanPromptFormatter() {
    }


    public static String createTrainingPlanPrompt(AiPlanRequest aiPlanRequest) {
        String template = """
                Create a personalized gym training plan for a user with the following parameters:
                1. Training goal: %s
                2. Level of advancement: %s
                3. Training days per week: %s
                4. Anthropometric data: gender:%s weight:%skg height:%s age:%s
                5. General health: %s
                6. Preferred training intensity: %s
                7. Special goal: %s

                Consider individual preferences and possible limitations. Adjust the plan to be effective and safe for the user.
                In the output, include only the training plan""";

        return String.format(template, aiPlanRequest.getGoal(), aiPlanRequest.getLevel(), aiPlanRequest.getDaysPerWeek(),
                aiPlanRequest.getGender(), aiPlanRequest.getWeight(), aiPlanRequest.getHeight(), aiPlanRequest.getAge(),
                aiPlanRequest.getHealth(), aiPlanRequest.getIntensity(), aiPlanRequest.getSpecialGoal());
    }
}