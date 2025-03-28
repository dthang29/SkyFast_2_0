package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/homepage")
public class RefundHistoryController {
    @Autowired
    private RefundService refundService;

    @Autowired
    private T_UserRepository userRepository;

    @GetMapping("/refund-history")
    public String getRefundHistory(Model model, Authentication authentication) {
        String email = authentication.getName(); // Lấy email user đăng nhập
        User user = userRepository.findByEmail(email);

        if (user != null) {
            model.addAttribute("refunds", refundService.getRefundsByUserId(user.getId()));
        }
        return "UserRefund";
    }
}
