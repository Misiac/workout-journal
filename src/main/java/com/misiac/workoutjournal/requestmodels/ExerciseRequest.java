package com.misiac.workoutjournal.requestmodels;

import lombok.Getter;

@Getter
public class ExerciseRequest {

    private long exerciseId;
    private float load;
    private int reps;
    private int setNumber;

}
