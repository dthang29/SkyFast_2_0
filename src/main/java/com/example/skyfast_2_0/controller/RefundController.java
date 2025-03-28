package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.service.RefundService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class RefundController {
    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @GetMapping("/refund")
    public String refundForm() {
        return "RefundRequire";
    }

    @PostMapping("/refund")
    public String requestRefundPost(@RequestParam("bookingId") String bookingCode, String reason, String bank, String bankNumber, Model model) {
        try{
            String message = refundService.RequestRefund(bookingCode, reason, bank, bankNumber);
            model.addAttribute("message", message);
            return "RefundRequire";
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            return "RefundRequire";
        }
    }

}
