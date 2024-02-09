package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.Workout;
import com.misiac.workoutjournal.repository.UserRepository;
import com.misiac.workoutjournal.repository.WorkoutRepository;
import com.misiac.workoutjournal.requestmodels.AiPlanRequest;
import org.springframework.ai.chat.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiService {

    private final ChatClient chatClient;
    private final UserRepository userRepository;
    private final WorkoutRepository workoutRepository;

    @Autowired
    public AiService(ChatClient chatClient, UserRepository userRepository, WorkoutRepository workoutRepository) {
        this.chatClient = chatClient;
        this.userRepository = userRepository;
        this.workoutRepository = workoutRepository;
    }


    public String getTrainingPlan(AiPlanRequest aiPlanRequest) {

        return chatClient.call(AiPromptCreator.createTrainingPlanPrompt(aiPlanRequest));
    }

    public String analyzeUserWorkouts(String email) {

//        User user = userRepository.findUserByEmail(email).orElseThrow(
//                () -> new EntityDoesNotExistException(USER_DOES_NOT_EXIST));

        List<Workout> workouts = workoutRepository.findWorkoutsByUserEmailOrderByDateDesc(
                email,
                PageRequest.of(0, 3)
        ).toList();

        String prompt = AiPromptCreator.createAnalyzeWorkoutPrompt(workouts);
        System.out.println(prompt);

        return chatClient.call(prompt);

    }
}