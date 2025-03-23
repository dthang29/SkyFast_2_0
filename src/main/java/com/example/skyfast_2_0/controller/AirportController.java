package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.AirportDTO;
import com.example.skyfast_2_0.dto.K_PromotionDTO;
import com.example.skyfast_2_0.service.AirportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/manager/airports")
public class AirportController {
    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public String getAllAirports(Model model) {
        List<AirportDTO> airports = airportService.getAllAirports();
        model.addAttribute("airports", airports);
        return "airportManagement";
    }

    @GetMapping("/new")
    public String getNewAirport(Model model) {
        model.addAttribute("airport", new AirportDTO());
        return "airportCreate";
    }

    @PostMapping("/create")
    public String createAirport(AirportDTO airportDTO, Model model) {
        try {
            AirportDTO createdAirport = airportService.createAirport(airportDTO);
            model.addAttribute("message", "Created airport successfully");
            model.addAttribute("promotion", new K_PromotionDTO());
            return "redirect:/manager/airports";
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage());
            model.addAttribute("airport", airportDTO);
            return "airportCreate";
        }
    }
}
