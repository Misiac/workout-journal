package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "musclegroups")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 45)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseType exercise;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private MuscleGroupCategory category;

    @Column(name = "is_primary", nullable = false)
    private Byte isPrimary;


    // MuscleGroup objects are equal, when exercise and category references are equal
    // A muscle group on an exercise can't be both primary and secondary
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MuscleGroup that = (MuscleGroup) o;

        if (!Objects.equals(exercise, that.exercise)) return false;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        int result = exercise != null ? exercise.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}