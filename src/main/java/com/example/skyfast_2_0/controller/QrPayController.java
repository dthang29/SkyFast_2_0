package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Payment;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_BookingRepository;
import com.example.skyfast_2_0.repository.T_PaymentRepository;
import com.example.skyfast_2_0.repository.T_PaymentTypeRepository;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.EmailSenderService;
import com.example.skyfast_2_0.service.UserProfileService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QrPayController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private T_UserRepository t_UserRepository;

    @Autowired
    private T_BookingRepository bookingRepository;

    @Autowired
    private T_PaymentRepository paymentRepository;

    @Autowired
    private T_PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    @GetMapping("/pay-with-qr")
    public String payWithQR(Model model, @RequestParam("bookingCode") String bookingCode, Authentication auth) {
        String email = userProfileService.getUserEmail(auth);
        User user = t_UserRepository.findByEmail(email);
        if (bookingCode == null) {
            return "redirect:/homepage";
        }
        if(user.getPhoneNumber() == null){
            user.setPhoneNumber("");
        }
        Booking booking = bookingRepository.findByBookingCode(bookingCode);
        if (booking == null) {
            return "redirect:/homepage";
        }
        model.addAttribute("user", user);
        model.addAttribute("booking", booking);
        model.addAttribute("bookingCode", bookingCode);
        return "QRPayment";
    }

    @PostMapping("/success-pay-qr")
    public String confirmQRPayment(@RequestParam("bookingCode") String bookingCode, Authentication auth) throws MessagingException {
        if (bookingCode == null) {
            return "redirect:/homepage";
        }
        Booking booking = bookingRepository.findByBookingCode(bookingCode);
        if (booking == null) {
            return "redirect:/homepage";
        }
        String email = userProfileService.getUserEmail(auth);
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentDate(java.time.LocalDateTime.now());
        payment.setPaymentType(paymentTypeRepository.findById(2));
        paymentRepository.save(payment);
        booking.setBookingStatus("Done");
        bookingRepository.save(booking);
        emailSenderService.successPayment(email, booking, payment);
        return "successPayment";
    }
}
