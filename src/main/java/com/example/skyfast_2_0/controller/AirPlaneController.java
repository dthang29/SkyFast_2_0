package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.AirPlaneService;
import com.example.skyfast_2_0.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/airplane")
public class AirPlaneController {
    @Autowired
    private AirPlaneService airPlaneService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private T_UserRepository t_userRepository;

    @GetMapping
    public String getAllAirplanes(Model model, Authentication authentication) {
        if(authentication != null){
            String email = userProfileService.getUserEmail(authentication);
            User user = t_userRepository.findByEmail(email);
            model.addAttribute("username", user.getUserName());
        }
        List<Airplane> airplanes = airPlaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        return "Airplane";
    }

    @GetMapping("/{id}")
    public String getAirplaneById(@PathVariable("id") int id, Model model, Authentication authentication) {
        if(authentication != null){
            String email = userProfileService.getUserEmail(authentication);
            User user = t_userRepository.findByEmail(email);
            model.addAttribute("username", user.getUserName());
        }
        Airplane airplane = airPlaneService.getAirplaneById(id);
        if (airplane == null) {
            throw new RuntimeException("Airplane not found with id: " + id);
        }
        model.addAttribute("airplane", airplane);
        return "Plane";
    }
}