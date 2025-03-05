package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.TicketDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;

import com.example.skyfast_2_0.entity.Ticket;

import com.example.skyfast_2_0.repository.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;


    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;

    }

    public List<TicketInfoDTO> getAllTickets() {
        List<Ticket> tickets = ticketRepository.findAll();
        return tickets.stream().map(ticket -> {
            TicketInfoDTO dto = new TicketInfoDTO(ticket);
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
    public TicketInfoDTO getTicketById(Integer id) {
        Optional<Ticket> ticketOptional = ticketRepository.findById(id);
        if (ticketOptional.isPresent()) {
            Ticket ticket = ticketOptional.get();
            TicketInfoDTO dto = new TicketInfoDTO(ticket);
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

    public TicketInfoDTO updateTicket(Integer id, TicketDTO ticketDTO) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(id);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStatus(ticketDTO.getStatus());
            ticket.setTicketPrice(ticketDTO.getTicketPrice());
            // Cập nhật các field khác nếu cần...
            ticketRepository.save(ticket);

            TicketInfoDTO dto = new TicketInfoDTO(ticket);
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
    public TicketInfoDTO createTicket(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        ticket.setStatus(ticketDTO.getStatus());
        ticket.setTicketPrice(ticketDTO.getTicketPrice());
        // Cập nhật các field khác nếu cần...
        ticket = ticketRepository.save(ticket);

        TicketInfoDTO dto = new TicketInfoDTO(ticket);
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
}
