package com.misiac.workoutjournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misiac.workoutjournal.entity.Workout;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
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
    @DisplayName("GetWorkoutsForUser normal conditions")
    void testGetWorkoutsForUser() throws Exception {

        Page<Workout> mockWorkouts = new PageImpl<>(Collections.singletonList(new Workout()));
        Pageable pageable = Pageable.ofSize(2);

        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);
        when(workoutService.getWorkoutsForUser(TEST_EMAIL, pageable)).thenReturn(mockWorkouts);

        mockMvc.perform(get("/api/workout")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(workoutService, times(1)).getWorkoutsForUser(eq(TEST_EMAIL), any(Pageable.class));
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
    @DisplayName("UpdateExercise normal conditions")
    void testUpdateExercise() throws Exception {
        ExerciseRequest exerciseRequest = new ExerciseRequest(1, 5L, 5, 1);

        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);

        mockMvc.perform(put("/api/workout/exercise/{id}", 1)
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseRequest)))
                .andExpect(status().isOk());

        verify(workoutService, times(1))
                .updateExercise(eq(TEST_EMAIL), eq(1L), any(ExerciseRequest.class));
    }

    @Test
    @DisplayName("DeleteExercise normal conditions")
    void testDeleteExercise() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);

        mockMvc.perform(delete("/api/workout/exercise/{id}", 1)
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(workoutService, times(1))
                .deleteExercise(TEST_EMAIL, 1L);
    }
}