package com.example.portfolioContact.service;

import com.example.portfolioContact.dto.ContactRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.to}")
    private String to;

    @Value("${app.mail.from}")
    private String from;

    public void sendContactEmail(ContactRequest request) throws MessagingException {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setFrom(from);
            helper.setTo(to);
            helper.setReplyTo(request.getEmail());
            helper.setSubject("New contact from portfolio: " + request.getName());

            String body = """
                <p><strong>Name:</strong> %s</p>
                <p><strong>Email:</strong> %s</p>
                <p><strong>Message:</strong><br>%s</p>
                """.formatted(
                    request.getName(),
                    request.getEmail(),
                    request.getMessage().replace("\n", "<br>")
            );
            helper.setText(body, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException e){
            throw new RuntimeException("Failed to send email");
        }
    }
}
