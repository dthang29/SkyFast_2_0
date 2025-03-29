package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Payment;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


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

    @Async
    public void successPayment(String recipient, String Username, Booking booking, Payment payment)
            throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(recipient);
        helper.setSubject("Payment Success");
        String htmlContent = "<html>"
                + "<body>"
                + "<h2>Your Booking is Payment successful</h2>"
                + "<br>" + " "
                + "<p>Customer: <b>" + Username + "</b></p>"
                + "<br>" + " "
                + "<p>Booking Code: " + booking.getBookingCode()
                + "<br>" + " "
                + "<b>Total Price: <h3>" + booking.getTotalPrice() + "</b></h3></p>"
                + "<br>"
                + "<p>Payment Date: " + payment.getPaymentDate() + "</p>"
                + "<br>"
                + "Please check your Booking Information in your Profile."
                + "</body>"
                + "</html>";
        helper.setText(htmlContent, true);
        emailSender.send(message);
    }
}
