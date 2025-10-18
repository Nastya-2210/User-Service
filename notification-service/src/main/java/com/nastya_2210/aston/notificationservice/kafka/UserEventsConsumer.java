package com.nastya_2210.aston.notificationservice.kafka;

import com.nastya_2210.aston.commondto.UserEventDTO;
import com.nastya_2210.aston.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventsConsumer {
    private final EmailService emailService;
    private static final Logger logger = LoggerFactory.getLogger(UserEventsConsumer.class);

    public UserEventsConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "user-events")
    public void listenKafkaEvents(UserEventDTO event) {
        String operation = event.getOperation();
        String email = event.getEmail();

        logger.info("Получено событие: операция={}, email={}", operation, email);

        if ("USER_CREATED".equals(operation)) {
            emailService.sendCreationEmail(email);
        } else if ("USER_DELETED".equals(operation)) {
            emailService.sendDeletionEmail(email);
        } else {
            logger.warn("Получена неизвестная операция: {}", operation);
        }
    }
}