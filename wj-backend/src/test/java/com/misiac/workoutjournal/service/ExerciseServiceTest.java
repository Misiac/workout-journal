package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.ExerciseType;
import com.misiac.workoutjournal.repository.ExerciseTypeRepository;
import com.misiac.workoutjournal.responsemodels.ExerciseTinyDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {
    @Mock
    private ExerciseTypeRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    @Test
    @DisplayName("GetAllExercisesTiny normal conditions")
    void getAllExercisesTiny() {

        ExerciseType exercise1 = new ExerciseType(1L, "Lat Raise", null, null);
        ExerciseType exercise2 = new ExerciseType(2L, "Biceps Curl", null, null);

        List<ExerciseType> exercises = List.of(exercise1, exercise2);

        Mockito.when(exerciseRepository.findAll()).thenReturn(exercises);

        List<ExerciseTinyDTO> returnList = exerciseService.getAllExercisesTiny();

        Assertions.assertEquals(exercise1.getId(), returnList.getFirst().getId());
        Assertions.assertEquals(exercise2.getId(), returnList.getLast().getId());

        Assertions.assertEquals(exercise1.getName(), returnList.getFirst().getName());
        Assertions.assertEquals(exercise2.getName(), returnList.getLast().getName());
    }
}