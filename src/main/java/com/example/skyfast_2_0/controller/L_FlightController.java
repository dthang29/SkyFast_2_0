package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.L_FlightDTO;
import com.example.skyfast_2_0.repository.AirPlaneRepository;
import com.example.skyfast_2_0.repository.AirlineRepository;
import com.example.skyfast_2_0.repository.AirportRepository;
import com.example.skyfast_2_0.repository.RouteRepository;
import com.example.skyfast_2_0.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class L_FlightController {

    @Autowired
    private L_FlightService flightService;

    @Autowired
    private L_AirlineService airlineService;

    @Autowired
    private L_AirplaneService airplaneService;

    @Autowired
    private L_RouteService routeService;

    @Autowired
    private AirlineRepository airlineRepository;

    @Autowired
    private AirPlaneRepository airPlaneRepository;

    @Autowired
    private AirportRepository airportRepository;

    @Autowired
    private RouteRepository routeRepository;
    @GetMapping("/flights")
    public String getAllFlights(Model model) {
        model.addAttribute("flights", flightService.getAllFlights());
        model.addAttribute("flight", new L_FlightDTO());
        model.addAttribute("airlines", airlineService.getAllAirlines());
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("airline", airlineRepository.findAllAirline());
        model.addAttribute("airplane", airPlaneRepository.findAllPlane());
        model.addAttribute("airport", airportRepository.findAllAirport());
        model.addAttribute("routeAll", routeRepository.findAll());
        return "flightManagement";
    }

    @GetMapping("/flight/{id}")
    public String getFlightById(@PathVariable Integer id, Model model) {
        model.addAttribute("flight", flightService.getFlightById(id));
        model.addAttribute("airlines", airlineService.getAllAirlines());
        model.addAttribute("airplanes", airplaneService.getAllAirplanes());
        model.addAttribute("routes", routeService.getAllRoutes());
        model.addAttribute("airline", airlineRepository.findAllAirline());
        model.addAttribute("airplane", airPlaneRepository.findAllPlane());
        model.addAttribute("airport", airportRepository.findAllAirport());
        model.addAttribute("routeAll", routeRepository.findAll());
        return "flightDetail";
    }

    @PostMapping("/create/flight")
    public String createFlight(
            @Valid @ModelAttribute L_FlightDTO flightDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors: " + bindingResult.getAllErrors());
            model.addAttribute("flight", flightDTO);
            model.addAttribute("airlines", airlineService.getAllAirlines());
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("routes", routeService.getAllRoutes());
            model.addAttribute("airline", airlineRepository.findAllAirline());
            model.addAttribute("airplane", airPlaneRepository.findAllPlane());
            model.addAttribute("airport", airportRepository.findAllAirport());
            model.addAttribute("routeAll", routeRepository.findAll());
            return "flightManagement"; // Trả về form nếu có lỗi
        }

        try {
            System.out.println("Creating flight with DTO: " + flightDTO);
            flightService.createFlight(flightDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Flight created successfully!");
        } catch (Exception e) {
            System.out.println("Error creating flight: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create flight: " + e.getMessage());
        }
        return "redirect:/manager/flights";
    }

    @PostMapping("/update/flight/{id}")
    public String updateFlight(
            @PathVariable Integer id,
            @Valid @ModelAttribute("flight") L_FlightDTO flightDTO,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding errors: " + bindingResult.getAllErrors());
            model.addAttribute("flight", flightDTO);
            model.addAttribute("airlines", airlineService.getAllAirlines());
            model.addAttribute("airplanes", airplaneService.getAllAirplanes());
            model.addAttribute("routes", routeService.getAllRoutes());
            return "flightDetail";
        }

        try {
            System.out.println("Updating flight with DTO: " + flightDTO);
            flightService.updateFlight(id, flightDTO);
            redirectAttributes.addFlashAttribute("successMessage", "Flight updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating flight: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update flight: " + e.getMessage());
        }
        return "redirect:/manager/flights";
    }

    @PostMapping("/delete/flight/{id}")
    public String deleteFlight(@PathVariable Integer id) {
        flightService.deleteFlight(id);
        return "redirect:/manager/flights";
    }
}