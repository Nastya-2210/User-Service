package com.nastya_2210.aston.notificationservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmailIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldSendCreationEmail_IntegrationTest() {
        String testEmail = "create-test@example.com";
        String url = "http://localhost:" + port + "/api/notifications/email/create?email=" + testEmail;

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains(testEmail);
    }

    @Test
    void shouldSendDeletionEmail_IntegrationTest() {
        String testEmail = "delete-test@example.com";
        String url = "http://localhost:" + port + "/api/notifications/email/delete?email=" + testEmail;

        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).contains(testEmail);
    }
}