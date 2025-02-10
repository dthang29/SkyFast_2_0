package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private FlightService flightService;
    @GetMapping
    @PostMapping
    public String getTop10CheapestTicketsPost(Model model) {
        return "Flight";
    }
}