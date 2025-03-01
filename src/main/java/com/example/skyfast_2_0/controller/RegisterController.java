package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.constant.Role;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.RegisterService;
import com.example.skyfast_2_0.service.VerifyEmailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @Autowired
    private VerifyEmailService verifyEmailService;

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "/auth/Register";
    }

    @PostMapping("/register")
    public String registerUser(Model model,
                               HttpSession session,
                               @ModelAttribute User user,
                               @RequestParam("confirmPass") String confirmPassword)
            throws MessagingException {

        if (registerService.checkEmailExist(user.getEmail())) {
            model.addAttribute("emailError", "Email already exists");
            return "/auth/Register";

        } else if (registerService.isOnlySpaces(user.getUserName())) {
            model.addAttribute("userSpaceError", "Username is only space !");
            return "/auth/Register";

        } else if (registerService.checkUsernameExist(user.getUserName())) {
            model.addAttribute("userError", "Username already exists");
            return "/auth/Register";

        } else if (registerService.checkPhoneExist(user.getPhoneNumber())) {
            model.addAttribute("phoneError", "Phone Number already exists");
            return "/auth/Register";
        }

        if (!registerService.checkPasswordSameWithConfirmPassword(user.getPassword(), confirmPassword)) {
            model.addAttribute("passError", "Password are not the same");
            return "/auth/Register";
        }

            verifyEmailService.sendUserEmail(user.getEmail());
            session.setAttribute("user", user);
            return "/auth/VerifyEmail";
        }

    @GetMapping("/resend-code")
    public String resendCode(HttpSession session,
                             Model model) throws MessagingException {
        User user = (User) session.getAttribute("user");
        verifyEmailService.sendUserEmail(user.getEmail());
        model.addAttribute("mess", "Code has been sent !");
        return "/auth/VerifyEmail";
    }

//    @PostMapping("/resend-code")
//    public String resendCode(HttpSession session,
//                             Model model)
//            throws MessagingException {
//        User user = (User) session.getAttribute("user");
//        verifyEmailService.sendUserEmail(user.getEmail());
//        model.addAttribute("mess", "Code has been sent !");
//        return "/auth/VerifyEmail";
//    }
}
