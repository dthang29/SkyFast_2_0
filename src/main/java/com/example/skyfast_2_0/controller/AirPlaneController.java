package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.service.AirPlaneService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public String getAllAirplanes(Model model) {
        List<Airplane> airplanes = airPlaneService.getAllAirplanes();
        model.addAttribute("airplanes", airplanes);
        return "AirPlane";
    }

    @GetMapping("/{id}")
    public String getAirplaneById(@PathVariable("id") int id, Model model) {
        Airplane airplane = airPlaneService.getAirplaneById(id);
        if (airplane == null) {
            throw new RuntimeException("Airplane not found with id: " + id);
        }
        model.addAttribute("airplane", airplane);
        return "Plane";
    }
}