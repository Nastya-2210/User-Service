package com.nastya_2210.aston.notificationservice.controller;

import com.nastya_2210.aston.notificationservice.service.EmailService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/email/create")
    public String sendCreationEmail(@RequestParam("email") String email) {
        emailService.sendCreationEmail(email);
        return "Email отправлен на: " + email;
    }

    @PostMapping("/email/delete")
    public String sendDeletionEmail(@RequestParam("email") String email) {
        emailService.sendDeletionEmail(email);
        return "Email отправлен на: " + email;
    }
}