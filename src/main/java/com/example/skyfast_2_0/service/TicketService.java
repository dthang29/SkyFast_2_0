package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> getTop10CheapestTickets() {
        return ticketRepository.findTop10CheapestTickets();
    }
}