package com.misiac.workoutjournal.requestmodels;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.misiac.workoutjournal.util.MessageProvider;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = MessageProvider.DATE_NULL)
    @PastOrPresent(message = MessageProvider.DATE_FUTURE)
    private LocalDateTime date;

    @Valid
    @NotEmpty(message = MessageProvider.EXERCISES_EMPTY)
    private List<ExerciseRequest> exercises;
}
