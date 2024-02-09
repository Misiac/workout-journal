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
                You are a user's personal trainer with access to user workout data(exercises, reps, sets, load in kg), you do not have access to user form or technique, so don't mention form.
                Include workout name and workout date in output. Do not write overall recommendations or other general recommendations.
                Your task is to analyze the user's workouts comprehensively and provide personalized insights and tips to optimize their training regimen.
                Consider each exercise the user has performed, including the number of reps, sets, and weight used.
                Evaluate trends in performance over time. Identify areas where the user can achieve better results,
                offer tailored recommendations such as suggesting exercise variations for balanced muscle development,
                recommending recovery strategies. If there is no need for improvement, don' write anything, you don't have to comment every exercise.
                Sort it by workout. Start the message with "Workout Analysis\"""";

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