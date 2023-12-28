package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.StatsService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = StatsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class StatsControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private StatsService statsService;
    @MockBean
    private JWTExtractor jwtExtractor;

    public static final String TEST_EMAIL = "test@email.com";
    public static final String TEST_TOKEN = "dummyToken";

    @Test
    @DisplayName("test GetTotalReps normal conditions")
    void testGetTotalReps() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getTotalReps(TEST_EMAIL)).thenReturn(40L);

        String response = mockMvc.perform(get("/api/stats/total/reps")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        verify(statsService, times(1)).getTotalReps(TEST_EMAIL);
        assertEquals(40L, Long.parseLong(response));
    }

    @Test
    @DisplayName("test GetTotalWorkouts normal conditions")
    void testGetTotalWorkouts() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getTotalWorkouts(TEST_EMAIL)).thenReturn(40L);

        String response = mockMvc.perform(get("/api/stats/total/workouts")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        verify(statsService, times(1)).getTotalWorkouts(TEST_EMAIL);
        assertEquals(40L, Long.parseLong(response));
    }

    @Test
    @DisplayName("test GetTotalSets normal conditions")
    void testGetTotalSets() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getTotalSets(TEST_EMAIL)).thenReturn(40L);

        String response = mockMvc.perform(get("/api/stats/total/sets")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        verify(statsService, times(1)).getTotalSets(TEST_EMAIL);
        assertEquals(40L, Long.parseLong(response));
    }

    @Test
    @DisplayName("test GetTotalVolume normal conditions")
    void testGetTotalVolume() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getTotalVolume(TEST_EMAIL)).thenReturn(452.5);

        String response = mockMvc.perform(get("/api/stats/total/volume")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();


        verify(statsService, times(1)).getTotalVolume(TEST_EMAIL);
        assertEquals(452.5, Double.parseDouble(response));
    }
}