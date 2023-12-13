package com.misiac.workoutjournal.requestmodels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddExerciseRequest {

    private int exerciseId;
    private float load;
    private int reps;
    private int setNumber;


}
