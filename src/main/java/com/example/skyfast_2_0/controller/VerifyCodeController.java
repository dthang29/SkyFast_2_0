package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.service.ForgotPasswordService;
import com.example.skyfast_2_0.service.VerifyEmailService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.skyfast_2_0.service.RegisterService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.skyfast_2_0.constant.Role;
import java.time.LocalDate;

@Controller
public class VerifyCodeController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegisterService registerService;

    @Autowired
    private VerifyEmailService verifyEmailService;


    @GetMapping("/verify-code")
    public String verifyCode() {
        return "/auth/VerifyCode";
    }

    @PostMapping("/verify-code")
    public String verifyCode(Model model
                             , HttpSession session
                             , @RequestParam("code") String code) {
        String email = (String) session.getAttribute("email");
        if (forgotPasswordService.checkCode(email, code)) {
            return "/auth/ChangePassword";
        }
        else{
            model.addAttribute("errorCodeMess", "Code is incorrect or expired !");
            return "/auth/VerifyCode";
        }
    }

    @GetMapping("/verify-email")
    public String verifyEmail() {
        return "/auth/VerifyEmail";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(Model model,
            HttpSession session,
            @RequestParam("code") String code) {
        User user = (User) session.getAttribute("user");
        if (verifyEmailService.checkCode(user.getEmail(), code)) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.CUSTOMER);
        user.setCreatedAt(LocalDate.now());
        user.setUpdateAt(LocalDate.now());
        user.setDateOfBirth(LocalDate.now());
        user.setStatus("Active");
        registerService.saveUser(user);
        session.removeAttribute("user");
        return "/auth/Login";
        }
        else{
            model.addAttribute("errorVerifyCodeMess", "Code is incorrect or expired !");
            return "/auth/VerifyEmail";
        }
    }
}
