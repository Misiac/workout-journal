package com.misiac.workoutjournal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "exercise_types")
public class ExerciseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "equipment_jointable",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<EquipmentCategory> equipmentCategories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();

    public ExerciseType(String name) {
        this.name = name;
    }

}
