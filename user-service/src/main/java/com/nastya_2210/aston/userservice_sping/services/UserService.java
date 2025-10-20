package com.nastya_2210.aston.userservice_sping.services;

import com.nastya_2210.aston.commondto.UserEventDTO;
import com.nastya_2210.aston.userservice_sping.exceptions.UserNotFoundException;
import com.nastya_2210.aston.userservice_sping.dto.UserRequestDTO;
import com.nastya_2210.aston.userservice_sping.dto.UserResponseDTO;
import com.nastya_2210.aston.userservice_sping.models.User;
import com.nastya_2210.aston.userservice_sping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final KafkaTemplate<String, UserEventDTO> kafkaTemplate;

   @Autowired
    public UserService(UserRepository userRepository, KafkaTemplate<String, UserEventDTO> kafkaTemplate) {
        this.userRepository = userRepository;
       this.kafkaTemplate = kafkaTemplate;
   }

    // User → UserResponseDTO
    private UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getEmail(),
                user.getCreatedAt()
        );
    }

    // UserRequestDTO → User (для создания)
    private User toEntity(UserRequestDTO userRequestDTO) {
        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setAge(userRequestDTO.getAge());
        user.setEmail(userRequestDTO.getEmail());
        return user;
    }

    public UserResponseDTO findUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        return toResponseDTO(user);
    }

    public void saveUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        // Преобразовываем DTO в Entity
        User user = toEntity(userRequestDTO);

        // Сохраняем Entity
        userRepository.save(user);

        UserEventDTO event = new UserEventDTO(user.getEmail(),"USER_CREATED");

        kafkaTemplate.send("user-events", event);
    }

    public void deleteUser(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));
        userRepository.delete(user);

        UserEventDTO event = new UserEventDTO(user.getEmail(), "USER_DELETED");
        kafkaTemplate.send("user-events", event);
    }

    public void updateUser(int id, UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            throw new IllegalArgumentException("User cannot be null");
        }

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + id + " not found"));

        existingUser.setName(userRequestDTO.getName());
        existingUser.setAge(userRequestDTO.getAge());
        existingUser.setEmail(userRequestDTO.getEmail());

        userRepository.save(existingUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    }


