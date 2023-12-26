package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.mapper.ExerciseMapper;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.MuscleGroupCategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

class AdminServiceTest {

    @ExtendWith(MockitoExtension.class)

    @Mock
    private EquipmentCategoryRepository equipmentCategoryRepository;
    @Mock
    private MuscleGroupCategoryRepository muscleGroupCategoryRepository;
    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseMapper exerciseMapper;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("")
    void addMuscleGroupCategory() {
    }

    @Test
    @DisplayName("")
    void addEquipmentCategory() {
    }

    @Test
    @DisplayName("")
    void addExercise() {
    }

    @Test
    @DisplayName("")
    void bindEquipmentCategory() {
    }

    @Test
    @DisplayName("")
    void unbindEquipmentCategory() {
    }

    @Test
    @DisplayName("")
    void bindMuscleCategory() {
    }

    @Test
    @DisplayName("")
    void unbindMuscleCategory() {
    }

    @Test
    @DisplayName("")
    void constructMuscleGroup() {
    }
}