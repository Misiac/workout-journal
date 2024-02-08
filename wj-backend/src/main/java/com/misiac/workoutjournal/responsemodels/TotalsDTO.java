package com.misiac.workoutjournal.responsemodels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TotalsDTO {
    private Long reps;
    private Double volume;
    private Long sets;
    private Long workouts;
}