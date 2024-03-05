package ovh.workoutjournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ovh.workoutjournal.entity.Workout;
import ovh.workoutjournal.service.WorkoutService;
import ovh.workoutjournal.util.JWTExtractor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
    @DisplayName("DeleteWorkout normal conditions")
    void testDeleteWorkout() throws Exception {
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);

        mockMvc.perform(delete("/api/workouts/{id}", 1)
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(workoutService, times(1)).deleteWorkout(TEST_EMAIL, 1L);
    }

    @Test
    @DisplayName("UpdateWorkout normal conditions")
    void testUpdateWorkout()   {
//TODO
    }

    @Test
    @DisplayName("GetSpecificWorkout normal conditions")
    void testGetSpecificWorkout() throws Exception {
        Workout workout = new Workout();
        when(jwtExtractor.extractTokenParameter(TEST_TOKEN, JWTExtractor.ExtractionType.EMAIL)).thenReturn(TEST_EMAIL);
        when(workoutService.getSpecificWorkout(TEST_EMAIL, 1L)).thenReturn(workout);

        mockMvc.perform(get("/api/workouts/{id}", 1)
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(workout)));

        verify(workoutService, times(1)).getSpecificWorkout(TEST_EMAIL, 1L);
    }

}