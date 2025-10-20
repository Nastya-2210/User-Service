package com.nastya_2210.aston.userservice_sping.dto;

import java.time.LocalDateTime;

public class UserResponseDTO {

    private Integer id;
    private String name;
    private int age;
    private String email;
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
