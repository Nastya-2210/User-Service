package com.nastya_2210.aston.userservice_sping.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public class UserResponseDTO {

    @Schema(description = "ID пользователя", example = "1")
    private Integer id;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;
    @Schema(description = "Возраст пользователя", example = "20")
    private int age;
    @Schema(description = "Email пользователя", example = "test@gmail.com")
    private String email;
    @Schema(description = "Время создания пользователя", example = "2025-07-12T15:30:00")
    private LocalDateTime createdAt;


    public UserResponseDTO() {}

    public UserResponseDTO(Integer id, String name, int age, String email, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.createdAt = createdAt;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}
