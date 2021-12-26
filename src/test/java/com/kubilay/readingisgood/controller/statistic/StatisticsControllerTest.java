package com.kubilay.readingisgood.controller.statistic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubilay.readingisgood.service.statistic.StatisticsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = StatisticsController.class)
public class StatisticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    StatisticsService statisticsService;

    @Test
    @WithMockUser(value = "testUser")
    void test_GetCustomerStatistic_Success() throws Exception {
        mockMvc.perform(get("/api/v1/statistics/statistic/{customerNo}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetCustomerStatistic_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/statistics/statistic/{customerNo}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }
}
