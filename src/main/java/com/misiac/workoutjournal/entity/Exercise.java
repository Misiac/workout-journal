package com.misiac.workoutjournal.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @ManyToMany
    @JoinTable(name = "equipment_jointable",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<EquipmentCategory> exerciseEquipmentCategories = new LinkedHashSet<>();

    @OneToMany(mappedBy = "exercise")
    private Set<MuscleGroup> muscleGroup = new LinkedHashSet<>();

}