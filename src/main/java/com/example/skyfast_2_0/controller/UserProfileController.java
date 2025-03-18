package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.constant.Role;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.LoginService;
import com.example.skyfast_2_0.service.UserProfileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping("/homepage")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private T_UserRepository userRepository;

    @GetMapping("/profile")
    public String editProfile(Model model, Authentication authentication){
        String email = userProfileService.getUserEmail(authentication);
        User user = userRepository.findByEmail(email);
        if (user.getFullName() == null) {
            user.setFullName("");
        }
        if (user.getAddress() == null) {
            user.setAddress("");
        }
        if (user.getPhoneNumber() == null) {
            user.setPhoneNumber("");
        }
        model.addAttribute("user", user);
        return "Profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") User updatedUser, Authentication authentication) {
        String email = userProfileService.getUserEmail(authentication);
        userProfileService.updateUserProfile(email, updatedUser);
        return "redirect:/homepage/profile";
    }
}
