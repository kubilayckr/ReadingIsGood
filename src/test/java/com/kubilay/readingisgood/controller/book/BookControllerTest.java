package com.kubilay.readingisgood.controller.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kubilay.readingisgood.model.dto.BookDTO;
import com.kubilay.readingisgood.service.book.BookService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@WebMvcTest(controllers = BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    BookService bookService;

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateUser_Created() throws Exception {
        mockMvc.perform(post("/api/v1/books/book")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateBookDTO(true))))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_CreateUser_BadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/books/book")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(generateBookDTO(false))))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetByBookNo_Success() throws Exception {
        mockMvc.perform(get("/api/v1/books/book/{bookNo}", 1L)
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_GetByBookNo_BadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/books/book/{bookNo}", -1L)
                        .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_AddBookStock_Success() throws Exception {
        mockMvc.perform(patch("/api/v1/books/book/{bookNo}", 1L)
                        .contentType("application/json")
                        .param("quantity", "1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "testUser")
    void test_AddBookStock_BadRequest() throws Exception {
        mockMvc.perform(patch("/api/v1/books/book/{bookNo}", -1L)
                        .contentType("application/json")
                        .param("quantity", "0"))
                .andExpect(status().isBadRequest());
    }

    private BookDTO generateBookDTO(boolean isValid) {
        return new BookDTO(isValid ? null : 1L , null, "Title", "Author", "Publisher", 10, BigDecimal.TEN);
    }
}
