package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.responsemodels.RadarDataDTO;
import com.misiac.workoutjournal.responsemodels.StatsDTO;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    @DisplayName("GetStats normal conditions")
    void testGetStats() throws Exception {
        StatsDTO expectedStatsDTO = new StatsDTO(1L, 2.0, 3L, 4L);
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);

        when(statsService.getTotalStats(TEST_EMAIL)).thenReturn(expectedStatsDTO);

                mockMvc.perform(get("/api/stats/total")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)) // Set the content type
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reps").value(expectedStatsDTO.getReps()))
                .andExpect(jsonPath("$.sets").value(expectedStatsDTO.getSets()))
                .andExpect(jsonPath("$.volume").value(expectedStatsDTO.getVolume()))
                .andExpect(jsonPath("$.workouts").value(expectedStatsDTO.getWorkouts()));

        verify(statsService, times(1)).getTotalStats(TEST_EMAIL);
    }

    @Test
    @DisplayName("GetRadarData normal conditions")
    void testGetRadarData() throws Exception {

        RadarDataDTO expectedRadarData = new RadarDataDTO(1L, 2L, 3L, 4L, 5L, 6L);
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);

        when(statsService.getRadarData(TEST_EMAIL)).thenReturn(expectedRadarData);

        mockMvc.perform(get("/api/stats/radar")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)) // Set the content type
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chest").value(expectedRadarData.getChest()))
                .andExpect(jsonPath("$.shoulders").value(expectedRadarData.getShoulders()))
                .andExpect(jsonPath("$.legs").value(expectedRadarData.getLegs()))
                .andExpect(jsonPath("$.core").value(expectedRadarData.getCore()))
                .andExpect(jsonPath("$.back").value(expectedRadarData.getBack()))
                .andExpect(jsonPath("$.arms").value(expectedRadarData.getArms()));

        verify(statsService, times(1)).getRadarData(TEST_EMAIL);

    }
}