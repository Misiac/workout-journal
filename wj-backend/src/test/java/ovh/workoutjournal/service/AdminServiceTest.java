package ovh.workoutjournal.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ovh.workoutjournal.entity.EquipmentCategory;
import ovh.workoutjournal.entity.ExerciseType;
import ovh.workoutjournal.entity.MuscleGroup;
import ovh.workoutjournal.entity.MuscleGroupCategory;
import ovh.workoutjournal.mapper.ExerciseMapper;
import ovh.workoutjournal.repository.EquipmentCategoryRepository;
import ovh.workoutjournal.repository.ExerciseTypeRepository;
import ovh.workoutjournal.repository.MuscleGroupCategoryRepository;
import ovh.workoutjournal.requestmodels.AdminCreateExerciseRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ovh.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest {


    @Mock
    private EquipmentCategoryRepository equipmentCategoryRepository;
    @Mock
    private MuscleGroupCategoryRepository muscleGroupCategoryRepository;
    @Mock
    private ExerciseTypeRepository exerciseTypeRepository;
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
        ExerciseType exercise = constructExercise();

        when(exerciseTypeRepository.findExerciseByName("Lat Raise")).thenReturn(Optional.empty());
        when(equipmentCategoryRepository.findEquipmentCategoryByName(anyString())).thenReturn(Optional.empty());
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName(anyString())).thenReturn(Optional.empty());
        when(exerciseMapper.toExercise(exerciseRequest)).thenReturn(exercise);

        adminService.addExercise(exerciseRequest);

        verify(equipmentCategoryRepository, times(1)).save(any(EquipmentCategory.class));
        verify(muscleGroupCategoryRepository, times(2)).save(any(MuscleGroupCategory.class));
        verify(exerciseTypeRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("BindEquipmentCategory normal conditions")
    void testBindEquipmentCategory() {
        ExerciseType exercise = constructExercise();
        EquipmentCategory equipmentCategory = new EquipmentCategory("Bench");

        when(exerciseTypeRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(equipmentCategoryRepository.findEquipmentCategoryByName("Bench")).thenReturn(Optional.of(equipmentCategory));

        adminService.bindEquipmentCategory(1L, "Bench");

        assertTrue(exercise.getEquipmentCategories().contains(equipmentCategory));
        verify(exerciseTypeRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("UnbindEquipmentCategory normal conditions")
    void testUnbindEquipmentCategory() {

        ExerciseType exercise = constructExercise();
        EquipmentCategory equipmentCategory = exercise.getEquipmentCategories().iterator().next();

        when(exerciseTypeRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(equipmentCategoryRepository.findEquipmentCategoryByName("Dumbbells")).thenReturn(Optional.of(equipmentCategory));

        adminService.unbindEquipmentCategory(1L, "Dumbbells");

        assertFalse(exercise.getEquipmentCategories().contains(equipmentCategory));
        verify(exerciseTypeRepository, times(1)).save(exercise);

    }

    @Test
    @DisplayName("BindMuscleCategory normal conditions")
    void testBindMuscleCategory() {
        ExerciseType exercise = constructExercise();
        MuscleGroupCategory mgc = new MuscleGroupCategory("Lats");
        MuscleGroup mg = new MuscleGroup(0L, exercise, mgc, (byte) 1);

        when(exerciseTypeRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName("Lats")).thenReturn(Optional.of(mgc));

        adminService.bindMuscleCategory(1L, "Lats", true);

        assertTrue(exercise.getMuscleGroups().contains(mg));
        verify(exerciseTypeRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("UnbindMuscleCategory normal conditions")
    void testUnbindMuscleCategory() {
        ExerciseType exercise = constructExercise();
        MuscleGroup mg = exercise.getMuscleGroups().iterator().next();
        MuscleGroupCategory mgc = exercise.getMuscleGroups().iterator().next().getCategory();

        when(exerciseTypeRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(muscleGroupCategoryRepository.findMuscleGroupCategoryByName("Shoulders")).thenReturn(Optional.of(mgc));

        adminService.unbindMuscleCategory(1L, "Shoulders");

        assertFalse(exercise.getMuscleGroups().contains(mg));
        verify(exerciseTypeRepository, times(1)).save(exercise);
    }

    @Test
    @DisplayName("ConstructMuscleGroup normal conditions")
    void testConstructMuscleGroup() {
        MuscleGroupCategory mgc = new MuscleGroupCategory("Lats");
        ExerciseType exercise = constructExercise();

        var result = adminService.constructMuscleGroup(mgc, true, exercise);

        assertEquals((byte) 1, result.getIsPrimary());
        assertEquals(mgc, result.getCategory());
        assertEquals(exercise, result.getExercise());
    }

    private ExerciseType constructExercise() {

        ExerciseType exercise = new ExerciseType();
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