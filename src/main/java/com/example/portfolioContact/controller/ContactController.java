package com.example.portfolioContact.controller;

import com.example.portfolioContact.dto.ContactRequest;
import com.example.portfolioContact.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
public class ContactController {

    private final EmailService emailService;

    @PostMapping
    public String sendContact(@Valid @RequestBody ContactRequest request){
        try {
            emailService.sendContactEmail(request);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return  "Message sent Successfully!";
    }
}
