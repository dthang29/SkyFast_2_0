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
import com.example.skyfast_2_0.service.VNPayService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    private T_BookingRepository bookingRepository;

    @Autowired
    private T_PaymentRepository paymentRepository;

    @Autowired
    private T_PaymentTypeRepository paymentTypeRepository;

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private T_UserRepository t_UserRepository;

    @PostMapping("/vn-pay")
    public String submitOrder(HttpServletRequest request, @RequestParam("bookingCode") String bookingCode, HttpSession session) {
        if (bookingCode == null) {
            return "redirect:/homepage";
        }
        session.setAttribute("bookingCode", bookingCode);
        Booking booking = bookingRepository.findByBookingCode(bookingCode);
        long orderTotal = Math.round(booking.getTotalPrice());
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(request, orderTotal , baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @GetMapping("/vnpay-payment-return")
    public String paymentCompleted(HttpServletRequest request, HttpSession session, Authentication auth) throws MessagingException {
        Booking booking = bookingRepository.findByBookingCode(session.getAttribute("bookingCode").toString());
        String email = userProfileService.getUserEmail(auth);
        int paymentStatus = vnPayService.orderReturn(request);
        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentType(paymentTypeRepository.findById(1));
        paymentRepository.save(payment);
        booking.setBookingStatus("Done");
        bookingRepository.save(booking);
        User user = t_UserRepository.findByEmail(email);
        String username = user.getUserName();
        emailSenderService.successPayment(email, username, booking, payment);
        session.removeAttribute("bookingCode");
        if(paymentStatus == 1){
            return "successPayment";
        }
        else {
            return "failedPayment";
        }
    }
}
