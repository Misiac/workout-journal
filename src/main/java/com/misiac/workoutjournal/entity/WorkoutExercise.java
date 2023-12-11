package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @Column(name = "`load`", nullable = false)
    private Float load;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

}