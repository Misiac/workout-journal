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
                4. Anthropometric data: gender:%s weight:%skg height:%scm age:%s
                5. General health: %s
                6. Preferred training intensity: %s
                7. Special goal: %s

                Write how many sets and reps. Focus solely on exercises, considering individual preferences, special goals, and any potential limitations.
                Includle only the exercises. Do not include notes or note and rest days.
                Maximum number of exercises per day is 6. Don't write anything past the plan""";

        return String.format(template, aiPlanRequest.getGoal(), aiPlanRequest.getLevel(), aiPlanRequest.getDaysPerWeek(),
                aiPlanRequest.getGender(), aiPlanRequest.getWeight(), aiPlanRequest.getHeight(), aiPlanRequest.getAge(),
                aiPlanRequest.getHealth().isEmpty() ? "No problems" : aiPlanRequest.getHealth(),
                aiPlanRequest.getIntensity(), aiPlanRequest.getSpecialGoal());
    }
}