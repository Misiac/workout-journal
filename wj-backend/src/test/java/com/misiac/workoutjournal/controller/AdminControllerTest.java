package com.misiac.workoutjournal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misiac.workoutjournal.exception.UnauthorizedException;
import com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest;
import com.misiac.workoutjournal.service.AdminService;
import com.misiac.workoutjournal.util.AdminValidator;
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

import java.util.List;

import static com.misiac.workoutjournal.requestmodels.AdminCreateExerciseRequest.MuscleGroupRequest;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AdminService adminService;
    @MockBean
    private AdminValidator adminValidator;
    public static final String TEST_TOKEN = "dummyToken";

    @Test
    @DisplayName("AddMuscleGroupCategory with Admin token")
    void testAddMuscleGroupCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(post("/api/admin/muscle-category")
                        .header("Authorization", TEST_TOKEN)
                        .param("name", "Shoulders"))
                .andExpect(status().isCreated());

        verify(adminService, times(1)).addMuscleGroupCategory(eq("Shoulders"));
    }

    @Test
    @DisplayName("AddMuscleGroupCategory without Admin token")
    void testAddMuscleGroupCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(post("/api/admin/muscle-category")
                        .header("Authorization", TEST_TOKEN)
                        .param("name", "Shoulders"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0)).addMuscleGroupCategory(any());

    }

    @Test
    @DisplayName("AddEquipmentCategory with Admin token")
    void testAddEquipmentCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(post("/api/admin/equipment-category")
                        .header("Authorization", TEST_TOKEN)
                        .param("name", "Bench"))
                .andExpect(status().isCreated());

        verify(adminService, times(1)).addEquipmentCategory(eq("Bench"));
    }

    @Test
    @DisplayName("AddEquipmentCategory without Admin token")
    void testAddEquipmentCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(post("/api/admin/equipment-category")
                        .header("Authorization", TEST_TOKEN)
                        .param("name", "Bench"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0)).addEquipmentCategory(any());
    }


    @Test
    @DisplayName("AddExercise with Admin token")
    void testAddExerciseAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);
        AdminCreateExerciseRequest request = new AdminCreateExerciseRequest(
                "Bench Dumbbell Press",
                List.of("Bench", "Dumbbell"),
                List.of(new MuscleGroupRequest("Shoulders", true),
                        new MuscleGroupRequest("Traps", false)));

        mockMvc.perform(post("/api/admin/exercise")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(adminService, times(1)).addExercise(eq(request));
    }

    @Test
    @DisplayName("AddExercise without Admin token")
    void testAddExerciseNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);
        AdminCreateExerciseRequest request = new AdminCreateExerciseRequest(
                "Bench Dumbbell Press",
                List.of("Bench", "Dumbbell"),
                List.of(new MuscleGroupRequest("Shoulders", true),
                        new MuscleGroupRequest("Traps", false)));

        mockMvc.perform(post("/api/admin/exercise")
                        .header("Authorization", TEST_TOKEN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0)).addExercise(any());
    }

    @Test
    @DisplayName("BindEquipmentCategory with Admin token")
    void testBindEquipmentCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(patch("/api/admin/exercise/{exerciseId}/equipment-categories/{categoryName}", 2, "Bench")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(adminService, times(1)).bindEquipmentCategory(eq(2L), eq("Bench"));
    }

    @Test
    @DisplayName("BindEquipmentCategory without Admin token")
    void testBindEquipmentCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(patch("/api/admin/exercise/{exerciseId}/equipment-categories/{categoryName}", 2, "Bench")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0)).bindEquipmentCategory(anyLong(), anyString());
    }

    @Test
    @DisplayName("UnbindEquipmentCategory with Admin token")
    void testUnbindEquipmentCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(delete("/api/admin/exercise/{exerciseId}/equipment-categories/{categoryName}", 2, "Bench")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(adminService, times(1)).unbindEquipmentCategory(eq(2L), eq("Bench"));
    }

    @Test
    @DisplayName("UnbindEquipmentCategory without Admin token")
    void testUnbindEquipmentCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(delete("/api/admin/exercise/{exerciseId}/equipment-categories/{categoryName}", 2, "Bench")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0)).unbindEquipmentCategory(anyLong(), anyString());
    }

    @Test
    @DisplayName("BindMuscleCategory with Admin token")
    void testBindMuscleCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(patch("/api/admin/exercise/{exerciseId}/muscle-categories/{categoryName}", 2, "Shoulders")
                        .header("Authorization", TEST_TOKEN)
                        .param("isPrimary", "true"))
                .andExpect(status().isOk());

        verify(adminService, times(1))
                .bindMuscleCategory(eq(2L), eq("Shoulders"), eq(true));

    }

    @Test
    @DisplayName("BindMuscleCategory without Admin token")
    void testBindMuscleCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(patch("/api/admin/exercise/{exerciseId}/muscle-categories/{categoryName}", 2, "Shoulders")
                        .header("Authorization", TEST_TOKEN)
                        .param("isPrimary", "true"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0))
                .bindMuscleCategory(anyLong(), anyString(), anyBoolean());
    }

    @Test
    @DisplayName("UnbindMuscleCategory with Admin token")
    void testUnbindMuscleCategoryAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(true);

        mockMvc.perform(delete("/api/admin/exercise/{exerciseId}/muscle-categories/{categoryName}", 2, "Shoulders")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isOk());

        verify(adminService, times(1))
                .unbindMuscleCategory(eq(2L), eq("Shoulders"));
    }

    @Test
    @DisplayName("UnbindMuscleCategory without Admin token")
    void testUnbindMuscleCategoryNonAdmin() throws Exception {
        when(adminValidator.validateAdmin(TEST_TOKEN)).thenReturn(false);

        mockMvc.perform(delete("/api/admin/exercise/{exerciseId}/muscle-categories/{categoryName}", 2, "Shoulders")
                        .header("Authorization", TEST_TOKEN))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertInstanceOf(UnauthorizedException.class, result.getResolvedException()));

        verify(adminService, times(0))
                .unbindMuscleCategory(anyLong(), anyString());
    }
}