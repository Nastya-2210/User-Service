package models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User{
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

@Column(name = "name")
private String name;

@Column(name = "age")
private int age;

@Column(name = "email")
private String email;

@Column(name = "created_at", columnDefinition = "TIMESTAMP")
private LocalDateTime createdAt;

// Конструкторы
public User(String name, int age, String email) {
    this.name = name;
    this.age = age;
    this.email = email;
    this.createdAt = LocalDateTime.now();
}

public User() {
    this.createdAt = LocalDateTime.now();
}

// Геттеры и сеттеры
public int getId() {
    return id;
}

public String getName() {
    return name;
}

public void setName(String name) {
    this.name = name;
}

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
    return age;
}

public void setAge(int age) {
    this.age = age;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}

@Override
public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", email='" + email + '\'' +
            ", createdAt=" + createdAt +
            '}';
}
}