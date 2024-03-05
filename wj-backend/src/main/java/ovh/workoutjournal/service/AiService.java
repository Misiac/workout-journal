package ovh.workoutjournal.service;

import ovh.workoutjournal.entity.Workout;
import ovh.workoutjournal.entity.WorkoutExercise;
import ovh.workoutjournal.repository.WorkoutRepository;
import ovh.workoutjournal.requestmodels.AiPlanRequest;
import ovh.workoutjournal.util.MessageProvider;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final WorkoutRepository workoutRepository;

    @Autowired
    public AiService(ChatClient chatClient, WorkoutRepository workoutRepository) {
        this.chatClient = chatClient;
        this.workoutRepository = workoutRepository;
    }


    public String getTrainingPlan(AiPlanRequest aiPlanRequest) {

        return chatClient.call(AiPromptCreator.createTrainingPlanPrompt(aiPlanRequest));
    }

    public String analyzeUserWorkouts(String email) {


        List<Workout> workouts = workoutRepository.findWorkoutsByUserEmailOrderByDateDesc(
                email,
                PageRequest.of(0, 3)
        ).toList();

        if (workouts.size() < 3) throw new IllegalArgumentException(MessageProvider.NOT_ENOUGH_WORKOUTS);
        for (Workout workout : workouts) {
            if (workout.getWorkoutExercises().size() < 2)
                throw new IllegalArgumentException(MessageProvider.NOT_ENOUGH_WORKOUTS);
            for (WorkoutExercise exercise : workout.getWorkoutExercises()) {
                if (exercise.getWorkoutExerciseSets().isEmpty())
                    throw new IllegalArgumentException(MessageProvider.NOT_ENOUGH_WORKOUTS);
            }
        }

        String prompt = AiPromptCreator.createAnalyzeWorkoutPrompt(workouts);

        return chatClient.call(prompt);

    }
}