package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.service.L_AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/manager")
public class L_AirlineController {

    @Autowired
    private L_AirlineService airlineService;

    @GetMapping("/airlinelist")
    public String getAllAirlines(Model model) {
        List<Airline> airlines = airlineService.getAllAirlines();
        model.addAttribute("airlines", airlines);
        return "airlineManagement";
    }

    @GetMapping("/airline/detail/{id}")
    public String getAirlineDetail(@PathVariable Integer id, Model model) {
        try {
            Airline airline = airlineService.getAirlineById(id);
            System.out.println("🔍 Debug: Airline ID = " + airline.getId());
            System.out.println("🔍 Debug: Airline Name = " + airline.getAirlineName());
            System.out.println("🔍 Debug: Founded Date = " + airline.getFoundedDate());

            model.addAttribute("airline", airline);
            return "airlineDetail";
        } catch (RuntimeException e) {
            return "redirect:/manager/airlinelist";
        }
    }

    @PostMapping("/create/airline")
    public String createAirline(
            @ModelAttribute Airline airline,
            @RequestParam("imageFile") MultipartFile imageFile,
            @RequestParam("foundedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate foundedDate,
            RedirectAttributes redirectAttributes) {
        try {
            airline.setFoundedDate(foundedDate);

            if (!imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                airline.setImage(fileName);
            }

            System.out.println("Received Founded Date: " + airline.getFoundedDate()); // Debug
            airlineService.createAirline(airline);
            redirectAttributes.addFlashAttribute("successMessage", "Airline created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create airline: " + e.getMessage());
        }
        return "redirect:/manager/airlinelist";
    }

    @PostMapping("/airline/update/{id}")
    public String updateAirline(
            @PathVariable Integer id,
            @ModelAttribute Airline airline,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            @RequestParam("foundedDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate foundedDate,
            RedirectAttributes redirectAttributes) {
        try {
            System.out.println("Updating Airline ID: " + id);
            airline.setFoundedDate(foundedDate);
            System.out.println("Received Founded Date: " + airline.getFoundedDate()); // Debug

            if (airline.getFoundedDate() == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Founded Date cannot be null!");
                return "redirect:/manager/airline/detail/" + id;
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                airline.setImage(fileName);
            }

            airline.setId(id);
            airlineService.updateAirline(airline);
            redirectAttributes.addFlashAttribute("successMessage", "Airline updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update airline: " + e.getMessage());
        }
        return "redirect:/manager/airlinelist";
    }

    @PostMapping("/delete/airline/{id}")
    public String deleteAirline(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            airlineService.deleteAirline(id);
            redirectAttributes.addFlashAttribute("successMessage", "Airline deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete airline: " + e.getMessage());
        }
        return "redirect:/manager/airlinelist";
    }
}