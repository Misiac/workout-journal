package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.Exercise;
import com.misiac.workoutjournal.entity.MuscleGroup;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import com.misiac.workoutjournal.mapper.ExerciseMapper;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
import com.misiac.workoutjournal.repository.ExerciseRepository;
import com.misiac.workoutjournal.repository.MuscleGroupCategoryRepository;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {


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
    @DisplayName("AddMuscleGroupCategory normal conditions")
    void testAddMuscleGroupCategory() {
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName("Shoulders")).thenReturn(Optional.empty());
        adminService.addMuscleGroupCategory("Shoulders");
        verify(muscleGroupCategoryRepository, times(1)).save(any(MuscleGroupCategory.class));
    }

    @Test
    @DisplayName("AddEquipmentCategory normal conditions")
    void testAddEquipmentCategory() {
        when(equipmentCategoryRepository.findEquipmentCategoryByName("Bench")).thenReturn(Optional.empty());
        adminService.addEquipmentCategory("Bench");
        verify(equipmentCategoryRepository, times(1)).save(any(EquipmentCategory.class));
    }

    @Test
    @DisplayName("AddExercises normal conditions")
    void testAddExercise() {
        AdminCreateExerciseRequest exerciseRequest = new AdminCreateExerciseRequest("Lat Raise",
                List.of("Dumbbells"),
                List.of(new MuscleGroupRequest("Shoulders", true), new MuscleGroupRequest("Lats", false))
        );
        Exercise exercise = constructExercise();

        when(exerciseRepository.findExerciseByName("Lat Raise")).thenReturn(Optional.empty());
        when(equipmentCategoryRepository.findEquipmentCategoryByName(anyString())).thenReturn(Optional.empty());
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName(anyString())).thenReturn(Optional.empty());
        when(exerciseMapper.toExercise(exerciseRequest)).thenReturn(exercise);

        adminService.addExercise(exerciseRequest);

        verify(equipmentCategoryRepository, times(1)).save(any(EquipmentCategory.class));
        verify(muscleGroupCategoryRepository, times(2)).save(any(MuscleGroupCategory.class));
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("BindEquipmentCategory normal conditions")
    void testBindEquipmentCategory() {
        Exercise exercise = constructExercise();
        EquipmentCategory equipmentCategory = new EquipmentCategory("Bench");

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(equipmentCategoryRepository.findEquipmentCategoryByName("Bench")).thenReturn(Optional.of(equipmentCategory));

        adminService.bindEquipmentCategory(1L, "Bench");

        assertTrue(exercise.getEquipmentCategories().contains(equipmentCategory));
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("UnbindEquipmentCategory normal conditions")
    void testUnbindEquipmentCategory() {

        Exercise exercise = constructExercise();
        EquipmentCategory equipmentCategory = exercise.getEquipmentCategories().getFirst();

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(equipmentCategoryRepository.findEquipmentCategoryByName("Dumbbells")).thenReturn(Optional.of(equipmentCategory));

        adminService.unbindEquipmentCategory(1L, "Dumbbells");

        assertFalse(exercise.getEquipmentCategories().contains(equipmentCategory));
        verify(exerciseRepository, times(1)).save(exercise);

    }

    @Test
    @DisplayName("BindMuscleCategory normal conditions")
    void testBindMuscleCategory() {
        Exercise exercise = constructExercise();
        MuscleGroupCategory mgc = new MuscleGroupCategory("Lats");
        MuscleGroup mg = new MuscleGroup(0L, exercise, mgc, (byte) 1);

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName("Lats")).thenReturn(Optional.of(mgc));

        adminService.bindMuscleCategory(1L, "Lats", true);

        assertTrue(exercise.getMuscleGroups().contains(mg));
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("UnbindMuscleCategory normal conditions")
    void testUnbindMuscleCategory() {
        Exercise exercise = constructExercise();
        MuscleGroup mg = exercise.getMuscleGroups().iterator().next();
        MuscleGroupCategory mgc = exercise.getMuscleGroups().iterator().next().getCategory();

        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName("Shoulders")).thenReturn(Optional.of(mgc));

        adminService.unbindMuscleCategory(1L, "Shoulders");

        assertFalse(exercise.getMuscleGroups().contains(mg));
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("ConstructMuscleGroup normal conditions")
    void testConstructMuscleGroup() {
        MuscleGroupCategory mgc = new MuscleGroupCategory("Lats");
        Exercise exercise = constructExercise();

        var result = adminService.constructMuscleGroup(mgc, true, exercise);

        assertEquals((byte) 1, result.getIsPrimary());
        assertEquals(mgc, result.getCategory());
        assertEquals(exercise, result.getExercise());
    }

    private Exercise constructExercise() {

        Exercise exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Lat Raise");
        exercise.getEquipmentCategories().add(new EquipmentCategory("Dumbbells"));
        exercise.getMuscleGroups().add(
                new MuscleGroup(1L, exercise,
                        new MuscleGroupCategory("Shoulders"),
                        (byte) 1));
        return exercise;
    }
}