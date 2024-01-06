package com.misiac.workoutjournal.requestmodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {

    private long exerciseId;
    private float load;
    private int reps;
    private int setNumber;

}
