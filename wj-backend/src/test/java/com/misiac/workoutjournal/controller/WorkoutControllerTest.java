package com.misiac.workoutjournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misiac.workoutjournal.requestmodels.ExerciseRequest;
import com.misiac.workoutjournal.requestmodels.WorkoutRequest;
import com.misiac.workoutjournal.service.WorkoutService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WorkoutController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class WorkoutControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private WorkoutService workoutService;
    @MockBean
    private JWTExtractor jwtExtractor;

    public static final String TEST_TOKEN = "dummyToken";
    public static final String TEST_EMAIL = "test@email.com";

    @Test
    @DisplayName("Add new workout normal conditions")
    void testAddNewWorkout() throws Exception {

        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);
        WorkoutRequest workoutRequest = new WorkoutRequest(LocalDateTime.now(),
                List.of(new ExerciseRequest(1L, 2F, 3, 1),
                        new ExerciseRequest(1L, 2F, 3, 2)));

        mockMvc.perform(post("/api/workout/new")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutRequest)))
                .andExpect(status().isCreated());

        verify(workoutService, times(1)).addWorkout(any(WorkoutRequest.class), eq(TEST_EMAIL));
    }

    @Test
    @DisplayName("DeleteWorkout normal conditions")
    void testDeleteWorkout() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);

        mockMvc.perform(delete("/api/workout/{id}", 1)
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(workoutService, times(1)).deleteWorkout(TEST_EMAIL, 1L);
    }

    @Test
    @DisplayName("UpdateExercises normal conditions")
    void testUpdateExercises() throws Exception {
        ExerciseRequest request1 = new ExerciseRequest(1L, 5F, 5, 1);
        ExerciseRequest request2 = new ExerciseRequest(2L, 10F, 10, 2);

        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);

        mockMvc.perform(put("/api/workout/exercise")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(request1, request2))))
                .andExpect(status().isOk());

        verify(workoutService, times(1))
                .updateExercises(eq(TEST_EMAIL), anyList());
    }

    @Test
    @DisplayName("DeleteExercises normal conditions")
    void testDeleteExercise() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);
        List<Long> deleteIds = new ArrayList<>(List.of(1L, 2L, 3L));

        mockMvc.perform(delete("/api/workout/exercise", 1)
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteIds)))
                .andExpect(status().isOk());

        verify(workoutService, times(1))
                .deleteExercises(eq(TEST_EMAIL), anyList());
    }
}