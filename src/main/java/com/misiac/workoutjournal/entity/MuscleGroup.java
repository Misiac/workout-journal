package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "musclegroups")
public class MuscleGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 45)
    private String id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private MuscleGroupCategory category;

    @Column(name = "is_primary")
    private Byte isPrimary;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MuscleGroup that = (MuscleGroup) o;

        if (!exercise.equals(that.exercise)) return false;
        if (!Objects.equals(category, that.category)) return false;
        return Objects.equals(isPrimary, that.isPrimary);
    }

    @Override
    public int hashCode() {
        int result = exercise.hashCode();
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (isPrimary != null ? isPrimary.hashCode() : 0);
        return result;
    }
}