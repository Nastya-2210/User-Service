package com.nastya_2210.aston.userservice_sping.integration;

import com.nastya_2210.aston.userservice_sping.dto.UserResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class UserIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void shouldPerformFullCrudCycle() {
        // 1. CREATE
        String userJson = """
        {
            "name": "Full Cycle User",
            "age": 28,
            "email": "fullcycle@test.com"
        }
        """;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(userJson, headers);

        ResponseEntity<String> createResponse = restTemplate.postForEntity(
                "/api/users",
                request,
                String.class
        );
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 2. READ
        ResponseEntity<UserResponseDTO[]> getAllResponse = restTemplate.getForEntity(
                "/api/users",
                UserResponseDTO[].class
        );
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        UserResponseDTO[] users = getAllResponse.getBody();
        assertThat(users).isNotEmpty();

        // Находим ID созданного пользователя
        UserResponseDTO createdUser = Arrays.stream(users)
                .filter(user -> "fullcycle@test.com".equals(user.getEmail()))
                .findFirst()
                .orElseThrow();
        int userId = createdUser.getId();

        // 3. READ
        ResponseEntity<UserResponseDTO> getResponse = restTemplate.getForEntity(
                "/api/users/" + userId,
                UserResponseDTO.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().getName()).isEqualTo("Full Cycle User");

        // 4. UPDATE
        String updateJson = """
        {
            "name": "Updated Full Cycle User",
            "age": 29,
            "email": "updated@test.com"
        }
        """;

        HttpEntity<String> updateRequest = new HttpEntity<>(updateJson, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange(
                "/api/users/" + userId,
                HttpMethod.PUT,
                updateRequest,
                String.class
        );
        assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 5. READ - Проверяем обновление
        ResponseEntity<UserResponseDTO> getAfterUpdateResponse = restTemplate.getForEntity(
                "/api/users/" + userId,
                UserResponseDTO.class
        );
        assertThat(getAfterUpdateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAfterUpdateResponse.getBody().getName()).isEqualTo("Updated Full Cycle User");
        assertThat(getAfterUpdateResponse.getBody().getEmail()).isEqualTo("updated@test.com");

        // 6. DELETE
        restTemplate.delete("/api/users/" + userId);
    }
    }
