package com.misiac.workoutjournal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout parentWorkout;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exerciseType;

    @Column(name = "`load`", nullable = false)
    private Float load;

    @Column(name = "reps", nullable = false)
    private Integer reps;

    @Column(name = "set_number", nullable = false)
    private Integer setNumber;


}