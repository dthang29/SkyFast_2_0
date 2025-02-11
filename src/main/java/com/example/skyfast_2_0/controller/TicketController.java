package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.TicketDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;
import com.example.skyfast_2_0.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public ResponseEntity<List<TicketInfoDTO>> getAllTickets() {
        List<TicketInfoDTO> tickets = ticketService.getAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketInfoDTO> getTicketById(@PathVariable Integer id) {
        TicketInfoDTO ticket = ticketService.getTicketById(id);
        return ticket != null ? ResponseEntity.ok(ticket) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketInfoDTO> updateTicket(@PathVariable Integer id, @RequestBody TicketDTO ticketDTO) {
        TicketInfoDTO updatedTicket = ticketService.updateTicket(id, ticketDTO);
        return updatedTicket != null ? ResponseEntity.ok(updatedTicket) : ResponseEntity.notFound().build();
    }
}
