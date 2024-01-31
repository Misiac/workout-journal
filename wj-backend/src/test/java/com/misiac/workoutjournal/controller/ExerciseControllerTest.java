package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.responsemodels.ExerciseTinyDTO;
import com.misiac.workoutjournal.service.ExerciseService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExerciseController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ExerciseControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ExerciseService exerciseService;

    @Test
    @DisplayName("GetAllExercisesTiny normal conditions")
    void testGetAllExercisesTiny() throws Exception {

        ExerciseTinyDTO exercise1 = new ExerciseTinyDTO(1L, "Exercise1",null);
        ExerciseTinyDTO exercise2 = new ExerciseTinyDTO(2L, "Exercise2",null);
        List<ExerciseTinyDTO> mockExerciseList = Arrays.asList(exercise1, exercise2);

        when(exerciseService.getAllExercisesTiny()).thenReturn(mockExerciseList);

        mockMvc.perform(get("/api/exercises/tiny"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Exercise1"))
                .andExpect(jsonPath("$[1].name").value("Exercise2"));

        verify(exerciseService, times(1)).getAllExercisesTiny();

    }
}