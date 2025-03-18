package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/homepage")
public class UserChangePasswordController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private T_UserRepository t_UserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/user-change-password")
    public String userChangePassword() {
        return "/UserChangePassword";
    }

    @PostMapping("/confirm-change")
    public String confirmChange(Model model,
                                Authentication authentication,
                                @RequestParam("oldPass") String oldPass,
                                @RequestParam("changePass") String password,
                                @RequestParam("changePassConfirm") String confirmPassword) {

        String email = userProfileService.getUserEmail(authentication);
        User user = t_UserRepository.findByEmail(email);

        if(!passwordEncoder.matches(oldPass, user.getPassword())) {
            model.addAttribute("errorMatches","Wrong password !");
            return "/UserChangePassword";
        }
        else if(!password.equals(confirmPassword) ) {
            model.addAttribute("changePassError", "Password are not the same !");
            return "/UserChangePassword";
        }
        else {
            user.setPassword(passwordEncoder.encode(password));
            t_UserRepository.save(user);
            model.addAttribute("changeSuccess", "Change Password Successful");
            return "/UserChangePassword";
        }
    }
}
