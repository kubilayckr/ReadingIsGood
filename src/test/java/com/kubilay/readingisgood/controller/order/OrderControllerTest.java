package com.kubilay.readingisgood.controller.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubilay.readingisgood.model.dto.OrderDetailDTO;
import com.kubilay.readingisgood.model.request.CreateOrderRequest;
import com.kubilay.readingisgood.service.order.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    OrderService orderService;

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateOrder_Created() throws Exception {
        mockMvc.perform(post("/api/v1/orders/order")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCreateOrderRequest(true))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateOrder_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/orders/order")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCreateOrderRequest(false))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrderByOrderNo_Success() throws Exception {
        mockMvc.perform(get("/api/v1/orders/order/{orderNo}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrderByOrderNo_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/orders/order/{orderNo}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrdersByCustomerNo_Success() throws Exception {
        mockMvc.perform(get("/api/v1/orders/customerOrder/{customerNo}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrdersByCustomerNo_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/orders/customerOrder/{customerNo}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrderByOrderDateBetweenStartDateAndEndDate_Success() throws Exception {
        mockMvc.perform(get("/api/v1/orders/customerOrder")
                        .contentType("application/json")
                        .param("startDate", "01-01-2001")
                        .param("endDate", "02-01-2001"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetOrderByOrderDateBetweenStartDateAndEndDate_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/orders/customerOrder")
                        .contentType("application/json")
                        .param("startDate", "01.01.2001")
                        .param("endDate", "01.01.2001"))
                .andExpect(status().isBadRequest());
    }

    private OrderDetailDTO generateOrderDetailDTO() {
        return new OrderDetailDTO(null, null, 1L, 1, BigDecimal.TEN);
    }

    private CreateOrderRequest generateCreateOrderRequest(boolean isValid) {
        return new CreateOrderRequest(isValid ? 1L : null, LocalDateTime.now(), List.of(generateOrderDetailDTO()));
    }
}
