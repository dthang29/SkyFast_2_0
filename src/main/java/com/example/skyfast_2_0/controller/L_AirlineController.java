package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.service.L_AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/airlines")
public class L_AirlineController {

    @Autowired
    private L_AirlineService LAirlineService;

    @GetMapping("/list")
    public String getAllAirlines(Model model) {
        List<Airline> airlines = LAirlineService.getAllAirlines();
        model.addAttribute("airlines", airlines);
        return "airlineManagement";
    }

    @GetMapping("/detail/{id}")
    public String getAirlineDetail(@PathVariable Integer id, Model model) {
        try {
            Airline airline = LAirlineService.getAirlineById(id);
            model.addAttribute("airline", airline);
            return "airlineDetail";
        } catch (RuntimeException e) {
            return "redirect:/airlines/list";
        }
    }

    @PostMapping("/create")
    public String createAirline(@ModelAttribute Airline airline, RedirectAttributes redirectAttributes) {
        try {
            LAirlineService.createAirline(airline);
            redirectAttributes.addFlashAttribute("successMessage", "Airline created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create airline: " + e.getMessage());
        }
        return "redirect:/airlines/list";
    }

    @PostMapping("/update/{id}")
    public String updateAirline(@PathVariable Integer id, @ModelAttribute Airline airline, RedirectAttributes redirectAttributes) {
        try {
            airline.setId(id);
            LAirlineService.updateAirline(airline);
            redirectAttributes.addFlashAttribute("successMessage", "Airline updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update airline: " + e.getMessage());
        }
        return "redirect:/airlines/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteAirline(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            LAirlineService.deleteAirline(id);
            redirectAttributes.addFlashAttribute("successMessage", "Airline deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete airline: " + e.getMessage());
        }
        return "redirect:/airlines/list";
    }
}