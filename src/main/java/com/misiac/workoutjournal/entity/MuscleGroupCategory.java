package com.misiac.workoutjournal.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercise_musclegroup_categories")
public class MuscleGroupCategory {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "category", nullable = false, length = 50)
    private String category;

}