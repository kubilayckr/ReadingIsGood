package com.kubilay.readingisgood.controller.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubilay.readingisgood.model.dto.BookDTO;
import com.kubilay.readingisgood.model.dto.CustomerDTO;
import com.kubilay.readingisgood.service.customer.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    CustomerService customerService;

    @Test
    @WithMockUser(value = "testUser")
    void test_GetCustomer_Success() throws Exception {
        mockMvc.perform(get("/api/v1/customers/customer/{customerNo}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetCustomer_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/customers/customer/{customerNo}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_UpdateCustomer_Success() throws Exception {
        mockMvc.perform(put("/api/v1/customers/customer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCustomerDTO(true))))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_UpdateCustomer_BadRequest() throws Exception {
        mockMvc.perform(put("/api/v1/customers/customer")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCustomerDTO(false))))
                .andExpect(status().isBadRequest());
    }

    private CustomerDTO generateCustomerDTO(boolean isValid) {
        return new CustomerDTO(isValid ? null : 1L, 1L, "Name", "Surname", "email@gmail.com");
    }
}
