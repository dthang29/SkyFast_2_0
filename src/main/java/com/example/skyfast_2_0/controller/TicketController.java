//package com.example.skyfast_2_0.controller;
//
//import com.example.skyfast_2_0.entity.Ticket;
//import com.example.skyfast_2_0.service.TicketService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@Controller
//@RequestMapping("/tickets")
//public class TicketController {
//
//    @Autowired
//    private TicketService ticketService;
//    @GetMapping
//    @PostMapping
//    public String getTop10CheapestTicketsPost(Model model) {
//        List<Ticket> tickets = ticketService.getTop10CheapestTickets();
//        model.addAttribute("tickets", tickets);
//        return "HomePage";
//    }
//}