package com.kubilay.readingisgood.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubilay.readingisgood.model.request.CreateUserRequest;
import com.kubilay.readingisgood.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateUser_Created() throws Exception {
        mockMvc.perform(post("/api/v1/users/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCreateUserRequest(true))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateUser_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/users/user")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateCreateUserRequest(false))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetUserById_Success() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/{id}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetUserById_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/users/user/{id}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    private CreateUserRequest generateCreateUserRequest(boolean isValid) {
        return new CreateUserRequest(isValid ? "Name" : null, "Surname", "email@gmail.com", "Password", "Type");
    }
}
