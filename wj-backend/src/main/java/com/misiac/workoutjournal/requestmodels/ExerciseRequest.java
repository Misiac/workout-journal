package com.misiac.workoutjournal.requestmodels;

import com.misiac.workoutjournal.util.MessageProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseRequest {

    @NotNull(message = MessageProvider.EXERCISE_ID_NULL)
    @Positive(message = MessageProvider.EXERCISE_ID_NEGATIVE)
    private Long id;

    @PositiveOrZero(message = MessageProvider.LOAD_NEGATIVE)
    private Float load;

    @NotNull(message = MessageProvider.REPS_NULL)
    @Positive(message = MessageProvider.REPS_NEGATIVE)
    private Integer reps;

    @NotNull(message = MessageProvider.SET_NUMBER_NULL)
    @Positive(message = MessageProvider.SET_NUMBER_NEGATIVE)
    private Integer setNumber;

    @Override
    public String toString() {
        return "ExerciseRequest{" +
                "exerciseId=" + id +
                ", load=" + load +
                ", reps=" + reps +
                ", setNumber=" + setNumber +
                '}';
    }
}
