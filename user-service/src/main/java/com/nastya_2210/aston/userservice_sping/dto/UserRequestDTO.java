package com.nastya_2210.aston.userservice_sping.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {


    @NotBlank
    @Schema(description = "Имя пользователя", example = "Иван")
    private String name;

    @Min(1)
    @Schema(description = "Возраст пользователя", example = "20")
    private int age;

    @Email
    @Schema(description = "Email пользователя", example = "test@gmail.com")
    private String email;

    public UserRequestDTO() {}

    public UserRequestDTO(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setAge(int age) { this.age = age; }
    public void setEmail(String email) { this.email = email; }
}
