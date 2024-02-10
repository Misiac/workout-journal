package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.entity.WorkoutExercise;
import com.misiac.workoutjournal.entity.WorkoutExerciseSet;
import com.misiac.workoutjournal.requestmodels.AiPlanRequest;

import java.util.List;

public class AiPromptCreator {
    private AiPromptCreator() {
    }


    public static String createTrainingPlanPrompt(AiPlanRequest aiPlanRequest) {
        String planPromptTemplate = """
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

        return String.format(planPromptTemplate, aiPlanRequest.getGoal(), aiPlanRequest.getLevel(), aiPlanRequest.getDaysPerWeek(),
                aiPlanRequest.getGender(), aiPlanRequest.getWeight(), aiPlanRequest.getHeight(), aiPlanRequest.getAge(),
                aiPlanRequest.getHealth().isEmpty() ? "No problems" : aiPlanRequest.getHealth(),
                aiPlanRequest.getIntensity(), aiPlanRequest.getSpecialGoal());
    }

    public static String createAnalyzeWorkoutPrompt(List<Workout> workouts) {
        String analyzePromptTemplate = """
                Your task is to comprehensively analyze the user's workouts and provide personalized insights and tips to optimize their training regimen.
                Consider each exercise the user has performed, including the number of reps, sets, and weight used.
                Evaluate trends in performance over time. Identify areas where the user can achieve better results, offering tailored recommendations for specific aspects of their workouts.
                Check if the user's workout plan is balanced across different muscle groups and fitness components.
                Identify instances where exercises are repeated excessively or lack variety within a single session, and provide recommendations to address these issues.
                Ensure that each recommendation is targeted at a specific aspect of the user's workout, such as exercise selection, load management, muscle balance, recovery strategies, or overall workout balance.
                Include the workout name and workout date in the output. Sort the analysis by workout date in descending order.
                Start the message with "Workout Analysis" to distinguish it from other communications.
                Do not mention form or technique, as it's not within your scope of access.
                Avoid writing overall or general recommendations. Each tip should be focused on a specific improvement area based on the user's workout data.
                Check everyting twice to be sure""";

        StringBuilder promptBuilder = new StringBuilder();
        promptBuilder.append(analyzePromptTemplate);
        promptBuilder.append("\nUser Data: ");

        int workoutCounter = 1;

        for (Workout workout : workouts) {

            promptBuilder.append("\nWorkout ").append(workoutCounter).append(":");
            promptBuilder.append("\nName: ").append(workout.getName());
            promptBuilder.append("\nDate: ").append(workout.getDate());

            for (WorkoutExercise exercise : workout.getWorkoutExercises()) {
                promptBuilder.append("\nExercise ").append(exercise.getSequenceNumber()).append(":");
                promptBuilder.append("\nType: ").append(exercise.getExerciseType().getName());
                promptBuilder.append("\nSets:");

                for (WorkoutExerciseSet set : exercise.getWorkoutExerciseSets()) {
                    promptBuilder.append("\nSet ").append(set.getSetNumber()).append(": ");
                    promptBuilder.append("Reps: ").append(set.getReps()).append(" ");
                    promptBuilder.append("Load: ").append(set.getLoad()).append("kg");
                }

            }
            workoutCounter++;
        }


        return promptBuilder.toString();

    }
}