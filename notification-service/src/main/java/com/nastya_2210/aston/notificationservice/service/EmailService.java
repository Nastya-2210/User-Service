package com.nastya_2210.aston.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Component
@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCreationEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Создание аккаунта");
        message.setText("Здравствуйте! Ваш аккаунт был успешно создан.");

        try {
            mailSender.send(message);
            logger.info("Email успешно отправлен на: {}", email);
        } catch (Exception e) {
            logger.error("Ошибка отправки email на {}: {}", email, e.getMessage());
        }
    }

    public void sendDeletionEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Удаление аккаунта");
        message.setText("Здравствуйте! Ваш аккаунт был удалён.");

        try {
            mailSender.send(message);
            logger.info("Email успешно отправлен на: {}", email);
        } catch (Exception e) {
            logger.error("Ошибка отправки email на {}: {}", email, e.getMessage());
        }
    }
}
