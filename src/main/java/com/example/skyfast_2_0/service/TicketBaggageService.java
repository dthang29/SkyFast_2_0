package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.repository.BaggageRepository;
import com.example.skyfast_2_0.repository.TicketBaggageRepository;
import com.example.skyfast_2_0.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketBaggageService {
    @Autowired
    private TicketBaggageRepository ticketBaggageRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private BaggageRepository baggageRepository;
    @Transactional
    public void insertTicketBaggage(Integer baggageId) {
        ticketBaggageRepository.insertTicketBaggage(ticketRepository.findTicketByMaxId(), baggageRepository.getBaggageById(baggageId));
    }
}
