package com.misiac.workoutjournal.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class AdminCreateExerciseRequest {

    public String name;
    public List<String> equipmentCategories;
    public List<MuscleGroupRequest> muscleGroups;


    @Getter
    @AllArgsConstructor
    public static class MuscleGroupRequest {

        private String name;
        private boolean isPrimary;

    }
}
