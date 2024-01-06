package com.misiac.workoutjournal.mapper;

import com.misiac.workoutjournal.entity.EquipmentCategory;
import com.misiac.workoutjournal.entity.MuscleGroupCategory;
import com.misiac.workoutjournal.repository.EquipmentCategoryRepository;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExerciseMapperTest {
    @Mock
    private EquipmentCategoryRepository ecRepo;
    @Mock
    private MuscleGroupCategoryRepository mgcRepo;
    @InjectMocks
    private ExerciseMapper exerciseMapper;

    @DisplayName("AdminCreateExerciseRequest to Exercise class")
    @Test
    void toExercise() {

        AdminCreateExerciseRequest request = new AdminCreateExerciseRequest(
                "Lat Raise",
                List.of("Dumbbells"),
                List.of(
                        new MuscleGroupRequest("Shoulders", true)));

        EquipmentCategory ec = new EquipmentCategory("Dumbbells");
        MuscleGroupCategory mgc1 = new MuscleGroupCategory();


        when(ecRepo.findEquipmentCategoryByName("Dumbbells")).thenReturn(Optional.of(ec));
        when(mgcRepo.findMuscleGroupCategoryByName("Shoulders")).thenReturn(Optional.of(mgc1));

        var result = exerciseMapper.toExercise(request);

        assertEquals("Lat Raise", result.getName());
        assertEquals("Dumbbells", result.getEquipmentCategories().getFirst().getName());
        assertEquals(mgc1,
                result.getMuscleGroups().iterator().next().getCategory()
        );
    }
}