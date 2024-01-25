package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "workout_exercise_sets")
public class WorkoutExerciseSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_exercise_id", nullable = false)
    private WorkoutExercise parentWorkoutExercise;

    @NotNull
    @Column(name = "`load`", nullable = false)
    private Float load;

    @NotNull
    @Column(name = "reps", nullable = false)
    private Integer reps;

    @NotNull
    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

}