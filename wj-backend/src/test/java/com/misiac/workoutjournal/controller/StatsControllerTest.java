package com.misiac.workoutjournal.controller;

import com.misiac.workoutjournal.service.StatsService;
import com.misiac.workoutjournal.util.JWTExtractor;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

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
    //TODO


}