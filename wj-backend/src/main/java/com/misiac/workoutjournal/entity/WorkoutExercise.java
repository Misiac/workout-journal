package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "workout_exercises")
public class WorkoutExercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "exercise_type_id", nullable = false)
    private ExerciseType exerciseType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout parentWorkout;

    @NotNull
    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @OneToMany(mappedBy = "parentWorkoutExercise")
    private List<WorkoutExerciseSet> workoutExerciseSets = new LinkedList<>();

}