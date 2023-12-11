package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

}