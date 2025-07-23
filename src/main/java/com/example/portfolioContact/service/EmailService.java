package com.example.portfolioContact.service;

import com.example.portfolioContact.dto.ContactRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String to;

    @Value("${app.mail.from}")
    private String from;

    public void sendContactEmail(ContactRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(from);
        message.setSubject("New Contact from Portfolio: " + request.getName());
        message.setText(
                "Name: " + request.getName() + "\n" +
                        "Email: " + request.getEmail() + "\n\n" +
                        "Message:\n" + request.getMessage()
        );

        mailSender.send(message);
    }
}
