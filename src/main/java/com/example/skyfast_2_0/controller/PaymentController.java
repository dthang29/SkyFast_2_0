package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.repository.T_BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {

    @GetMapping("/select-payment-method")
    public String selectPaymentMethod(Model model, @RequestParam("bookingCode") String bookingCode) {
        if (bookingCode == null) {
            return "redirect:/homepage";
        }
        model.addAttribute("bookingCode", bookingCode);
        return "selectPayment";
    }

}
