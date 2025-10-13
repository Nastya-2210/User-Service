package com.nastya_2210.aston.userservice_sping.services;

import com.nastya_2210.aston.userservice_sping.exceptions.UserNotFoundException;
import com.nastya_2210.aston.userservice_sping.dto.UserRequestDTO;
import com.nastya_2210.aston.userservice_sping.dto.UserResponseDTO;
import com.nastya_2210.aston.userservice_sping.models.User;
import com.nastya_2210.aston.userservice_sping.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRequestDTO testUserRequestDTO;
    private UserResponseDTO testUserResponseDTO;

    @BeforeEach
    void setUp() {
        // Entity для работы с Repository
        testUser = new User("Test User", 25, "test@email.com");
        testUser.setId(1);
        testUser.setCreatedAt(LocalDateTime.now());

        // Request DTO для входных данных (создание/обновление)
        testUserRequestDTO = new UserRequestDTO("Test User", 25, "test@email.com");

        // Response DTO для ожидаемых выходных данных
        testUserResponseDTO = new UserResponseDTO(1, "Test User", 25, "test@email.com", LocalDateTime.now());
    }

    @Test
    void shouldFindUserById() {
        // given
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        // when
        UserResponseDTO result = userService.findUser(1);

        // then
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getName()).isEqualTo("Test User");
    }

    @Test
    void shouldSaveUser() {
        // given
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        // when
        userService.saveUser(testUserRequestDTO);
        // then
        verify(userRepository).save(any(User.class));
    }

    @Test
    void shouldDeleteUser(){
        // given
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository).findById(userId);
        verify(userRepository).delete(testUser);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound(){
        int userId = 55;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.findUser(userId))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with ID 55 not found");
    }

    @Test
    void shouldFindAllUsers(){
        // given
        List<User> users = List.of(testUser);
        when(userRepository.findAll()).thenReturn(users);

        //when
        userService.findAllUsers();

        //then
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

}
