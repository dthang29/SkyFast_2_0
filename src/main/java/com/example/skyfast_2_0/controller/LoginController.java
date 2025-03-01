package com.example.skyfast_2_0.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import com.example.skyfast_2_0.service.LoginService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()
                && !(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        }
        if(error != null){
            model.addAttribute("error", "Invalid username or password");
        }
        return "/auth/Login";
    }

//    @PostMapping("/login")
//    public String loginUser(Model model,
//                            @RequestParam("input_text") String text,
//                            @RequestParam("password") String password) {
//        if (loginService.getUserByEmail(text) && BCrypt.checkpw(password,
//                loginService.getUserPasswordByEmail(text))) {
//            return "/SkyFast_2_0";
//        }
//        else if (loginService.getUserByUsername(text) && BCrypt.checkpw(password,
//                loginService.getUserPasswordByUsername(text))) {
//            return "/SkyFast_2_0";
//        }
//        else {
//                model.addAttribute("Error", "Password or Username or Email is incorrect");
//                return "/auth/Login";
//        }
//    }
}
