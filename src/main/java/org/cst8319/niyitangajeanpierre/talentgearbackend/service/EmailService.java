package org.cst8319.niyitangajeanpierre.talentgearbackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;
    public void sendSimpleMessage(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("${SPRING_MAIL_USERNAME}");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);

    }
}
