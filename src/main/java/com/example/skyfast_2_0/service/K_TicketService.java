package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.K_TicketDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;

import com.example.skyfast_2_0.entity.*;

import com.example.skyfast_2_0.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class K_TicketService {

    @Autowired
    private K_BookingRepository KBookingRepository;

    @Autowired
    private K_FlightRepository KFlightRepository;

    @Autowired
    private K_SeatRepository KSeatRepository;

    @Autowired
    private K_PassengerRepository KPassengerRepository;

    private final K_TicketRepository KTicketRepository;


    public K_TicketService(K_TicketRepository KTicketRepository) {
        this.KTicketRepository = KTicketRepository;

    }

    public List<K_TicketInfoDTO> getAllTickets() {
        List<Ticket> tickets = KTicketRepository.findAll();
        return tickets.stream().map(ticket -> {
            K_TicketInfoDTO dto = new K_TicketInfoDTO(ticket);
            dto.setId(ticket.getId());
            dto.setStatus(ticket.getStatus());
            dto.setTicketPrice(ticket.getTicketPrice());

            // Lấy bookingId từ đối tượng booking nếu không null
            if(ticket.getBooking() != null) {
                dto.setBookingId(ticket.getBooking().getId());
            }

            // Lấy các trường từ các đối tượng quan hệ
            dto.setFlightNumber(ticket.getFlight().getFlightNumber());
            dto.setSeatNumber(ticket.getSeat().getSeatNumber());
            dto.setPassengerFullName(ticket.getPassenger().getFullName());
            return dto;
        }).collect(Collectors.toList());
    }
    public K_TicketInfoDTO getTicketById(Integer id) {
        Optional<Ticket> ticketOptional = KTicketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            K_TicketInfoDTO dto = new K_TicketInfoDTO(ticket);
            dto.setId(ticket.getId());
            dto.setStatus(ticket.getStatus());
            dto.setTicketPrice(ticket.getTicketPrice());
            if (ticket.getBooking() != null) {
                dto.setBookingId(ticket.getBooking().getId());
            }
            dto.setFlightNumber(ticket.getFlight().getFlightNumber());
            dto.setSeatNumber(ticket.getSeat().getSeatNumber());
            dto.setPassengerFullName(ticket.getPassenger().getFullName());
            return dto;
        }
        return null;
    }

    public K_TicketInfoDTO updateTicket(Integer id, K_TicketDTO KTicketDTO) {
        Optional<Ticket> optionalTicket = KTicketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStatus(KTicketDTO.getStatus());
            ticket.setTicketPrice(KTicketDTO.getTicketPrice());
            // Cập nhật các field khác nếu cần...
            KTicketRepository.save(ticket);

            K_TicketInfoDTO dto = new K_TicketInfoDTO(ticket);
            dto.setId(ticket.getId());
            dto.setStatus(ticket.getStatus());
            dto.setTicketPrice(ticket.getTicketPrice());
            if (ticket.getBooking() != null) {
                dto.setBookingId(ticket.getBooking().getId());
            }
            dto.setFlightNumber(ticket.getFlight().getFlightNumber());
            dto.setSeatNumber(ticket.getSeat().getSeatNumber());
            dto.setPassengerFullName(ticket.getPassenger().getFullName());
            return dto;
        }
        return null;
    }

}

