package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.service.ForgotPasswordService;
import com.example.skyfast_2_0.service.LoginService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@Controller
public class ForgotPasswordController {
    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private LoginService loginService;

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "/auth/ForgotPassword";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(Model model,
                                 @RequestParam("email") String email,
                                 HttpSession session)
            throws MessagingException {
        if (loginService.getUserByEmail(email)) {
            forgotPasswordService.sendUserEmail(email);
            session.setAttribute("email", email);
            return "/auth/VerifyCode";
        } else {
            model.addAttribute("errorMess", "Email is incorrect !");
            return "/auth/ForgotPassword";
        }
    }

    @GetMapping("/resend")
    public String resendCode(HttpSession session,
                             Model model) throws MessagingException {
        String email = (String) session.getAttribute("email");
        forgotPasswordService.sendUserEmail(email);
        model.addAttribute("mess", "Code has been sent !");
        return "/auth/VerifyCode";
    }
}


