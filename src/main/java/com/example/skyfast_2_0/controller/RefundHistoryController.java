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
    public String showRefundHistory(Model model, Authentication authentication) {
        // Lấy thông tin người dùng hiện tại
        String email = refundService.getUserEmail(authentication);
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "redirect:/login"; // Nếu không tìm thấy user, chuyển về trang đăng nhập
        }

        // Lấy danh sách hoàn tiền theo userId
        model.addAttribute("refunds", refundService.getRefundsByUserId(user.getId()));

        return "UserRefund"; // Điều hướng đến trang refundHistory.html
    }
}
