package com.misiac.workoutjournal.requestmodels;

import com.misiac.workoutjournal.util.MessageProvider;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminCreateExerciseRequest {

    @NotBlank(message = MessageProvider.REQUEST_NAME_BLANK)
    public String name;
    public List<String> equipmentCategories;
    public List<MuscleGroupRequest> muscleGroups;


    @Getter
    @AllArgsConstructor
    public static class MuscleGroupRequest {

        private String name;
        private Boolean isPrimary;

    }
}
