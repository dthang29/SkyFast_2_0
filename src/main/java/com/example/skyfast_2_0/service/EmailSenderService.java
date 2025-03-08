package com.example.skyfast_2_0.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Async
    public void sendEmail(String recipient, String content)
    throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(fromEmail);
        helper.setTo(recipient);
        helper.setSubject("Verification Code");
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>Your verification code</h2>"
                + "<p>Your verification code is: "
                + "<br>" + " "
                + "<b><h3>" + content + "</b></h3></p>"
                + "<p>This verification code will expire in 5 minutes.</p>"
                + "<br>"
                + "</body>"
                + "</html>";
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}
