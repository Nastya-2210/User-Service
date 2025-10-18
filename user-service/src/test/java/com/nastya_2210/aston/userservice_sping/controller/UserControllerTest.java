package com.nastya_2210.aston.userservice_sping.controller;

import com.nastya_2210.aston.userservice_sping.controller.UserController;
import com.nastya_2210.aston.userservice_sping.dto.UserRequestDTO;
import com.nastya_2210.aston.userservice_sping.dto.UserResponseDTO;
import com.nastya_2210.aston.userservice_sping.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // Тестовые данные
    private UserResponseDTO testUserResponse;
    private UserRequestDTO testUserRequest;
    @BeforeEach
    void setUp() {
        testUserResponse = new UserResponseDTO(1, "Test User", 25, "test@email.com", LocalDateTime.now());
        testUserRequest = new UserRequestDTO("Test User", 25, "test@email.com");
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        // given
        List<UserResponseDTO> users = List.of(testUserResponse);
        when(userService.findAllUsers()).thenReturn(users);

        // when & then
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Test User"))
                .andExpect(jsonPath("$[0].email").value("test@email.com"));
    }

    @Test
    void shouldGetUserById() throws Exception {
        // given
        int userId = 1;
        when(userService.findUser(userId)).thenReturn(testUserResponse);

        // when & then
        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.email").value("test@email.com"));
    }

    @Test
    void shouldCreateUser() throws Exception{
        UserRequestDTO validUserRequest = new UserRequestDTO("New User", 30, "new@email.com");

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "New User",
                    "age": 30,
                    "email": "new@email.com"
                }
                """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        int userId = 1;
        UserRequestDTO updateRequest = new UserRequestDTO("Updated Name", 35, "updated@email.com");

        mockMvc.perform(put("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                {
                    "name": "Updated Name",
                    "age": 35,
                    "email": "updated@email.com"
                }
                """))
                .andExpect(status().isOk());

        verify(userService).updateUser(eq(userId), any(UserRequestDTO.class));
    }

    @Test
    void shouldDeleteUser() throws Exception{
        // given
        int userId = 1;

        // when & then
        mockMvc.perform(delete("/api/users/{id}", userId))  // ← DELETE метод!
                .andExpect(status().isOk());

        verify(userService).deleteUser(userId);
    }
    }
