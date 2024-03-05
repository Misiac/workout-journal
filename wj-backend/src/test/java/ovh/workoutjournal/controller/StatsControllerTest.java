package ovh.workoutjournal.controller;

import ovh.workoutjournal.responsemodels.RadarDataDTO;
import ovh.workoutjournal.responsemodels.StatsDTO;
import ovh.workoutjournal.responsemodels.TotalsDTO;
import ovh.workoutjournal.service.StatsService;
import ovh.workoutjournal.util.JWTExtractor;
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
        StatsDTO expectedStatsDTO = new StatsDTO(new TotalsDTO(1L, 2.0, 3L, 4L), null);
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getStatsData(TEST_EMAIL)).thenReturn(expectedStatsDTO);

        mockMvc.perform(get("/api/stats")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalsDTO.reps").value(expectedStatsDTO.getTotalsDTO().getReps()))
                .andExpect(jsonPath("$.totalsDTO.sets").value(expectedStatsDTO.getTotalsDTO().getSets()))
                .andExpect(jsonPath("$.totalsDTO.volume").value(expectedStatsDTO.getTotalsDTO().getVolume()))
                .andExpect(jsonPath("$.totalsDTO.workouts").value(expectedStatsDTO.getTotalsDTO().getWorkouts()));

        verify(statsService, times(1)).getStatsData(TEST_EMAIL);
    }

    @Test
    @DisplayName("GetRadarData normal conditions")
    void testGetRadarData() throws Exception {
        RadarDataDTO expectedRadarData = new RadarDataDTO(1L, 2L, 3L, 4L, 5L, 6L);
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL))
                .thenReturn(TEST_EMAIL);
        when(statsService.getStatsData(TEST_EMAIL)).thenReturn(new StatsDTO(null, expectedRadarData));

        mockMvc.perform(get("/api/stats")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.radarDataDTO.chest").value(expectedRadarData.getChest()))
                .andExpect(jsonPath("$.radarDataDTO.shoulders").value(expectedRadarData.getShoulders()))
                .andExpect(jsonPath("$.radarDataDTO.legs").value(expectedRadarData.getLegs()))
                .andExpect(jsonPath("$.radarDataDTO.core").value(expectedRadarData.getCore()))
                .andExpect(jsonPath("$.radarDataDTO.back").value(expectedRadarData.getBack()))
                .andExpect(jsonPath("$.radarDataDTO.arms").value(expectedRadarData.getArms()));

        verify(statsService, times(1)).getStatsData(TEST_EMAIL);
    }
}