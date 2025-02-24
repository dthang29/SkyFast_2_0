package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.service.FlightService;
import com.example.skyfast_2_0.service.RouteService;
import com.example.skyfast_2_0.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/homepage")
public class HomePageController {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private FlightService flightService;
    @Autowired
    private RouteService routeService;
    @GetMapping
    @PostMapping
    public String homePagePost(Model model) {
        List<Ticket> tickets = ticketService.getTop10CheapestTickets();
        model.addAttribute("tickets", tickets);

        LocalDate date = LocalDate.of(2025, 3, 1);
        List<Flight> flights = flightService.getFlightsbyDepaTime(date);
        model.addAttribute("flights", flights);

        List<Object[]> routes = routeService.getAllRoutesWithAirportNames();
        Map<Integer, String[]> routeMap = new HashMap<>();
        for (Object[] route : routes) {
            routeMap.put((Integer) route[0], new String[]{(String) route[1], (String) route[2]});
        }
        model.addAttribute("routeMap", routeMap);
        return "HomePage";
    }
}
