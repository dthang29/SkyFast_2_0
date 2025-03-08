package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ResetPasswordController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private T_UserRepository t_UserRepository;

    @GetMapping("/reset-password")
    public String resetPassword() {
        return "/auth/ResetPassword";
    }

    @PostMapping("/reset-password")
    public String resetPassword(Model model,
                                HttpSession session,
                                @RequestParam("resetPass") String password,
                                @RequestParam("resetPassConfirm") String confirmPassword) {
        if(!password.equals(confirmPassword)) {
            model.addAttribute("resetPassError", "Password are not the same");
            return "/auth/ResetPassword";
        }
        else {
            String email = (String) session.getAttribute("email");
            User user = t_UserRepository.findByEmail(email);
            if (user != null) {
                user.setPassword(passwordEncoder.encode(password));
                t_UserRepository.save(user);
                session.removeAttribute(email);
                return "/auth/Login";
            } else {
                model.addAttribute("resetPassError", "User not found");
                return "/auth/ResetPassword";
            }
        }
    }
}
