package com.misiac.workoutjournal.requestmodels;

import lombok.Getter;

@Getter
public class ExerciseRequest {

    private int exerciseId;
    private float load;
    private int reps;
    private int setNumber;


}