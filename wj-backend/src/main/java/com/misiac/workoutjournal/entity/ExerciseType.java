package com.misiac.workoutjournal.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.misiac.workoutjournal.util.MessageProvider;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "exercise_types")
public class ExerciseType {

    @NotNull(message = MessageProvider.EXERCISE_TYPE_ID_NULL)
    @Positive(message = MessageProvider.EXERCISE_TYPE_ID_NEGATIVE)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @JsonIgnore
    @Column(name = "image")
    private byte[] image;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "equipment_jointable",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<EquipmentCategory> equipmentCategories = new LinkedHashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MuscleGroup> muscleGroups = new LinkedHashSet<>();
}

