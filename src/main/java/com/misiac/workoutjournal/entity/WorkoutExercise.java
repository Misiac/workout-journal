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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @Column(name = "`load`", nullable = false)
    private Float load;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

}