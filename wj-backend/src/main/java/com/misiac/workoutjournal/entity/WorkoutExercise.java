package com.misiac.workoutjournal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.misiac.workoutjournal.util.MessageProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    @NotNull(message = MessageProvider.EXERCISE_TYPE_NULL)
    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_type_id", nullable = false)
    private ExerciseType exerciseType;

    @JsonBackReference
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout parentWorkout;

    @NotNull(message = MessageProvider.SEQUENCE_NUMBER_NULL)
    @Positive(message = MessageProvider.SEQUENCE_NUMBER_NEGATIVE)
    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @JsonManagedReference
    @OneToMany(mappedBy = "parentWorkoutExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkoutExerciseSet> workoutExerciseSets = new LinkedList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WorkoutExercise exercise = (WorkoutExercise) o;

        return Objects.equals(id, exercise.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}