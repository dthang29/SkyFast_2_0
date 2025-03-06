package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.TicketDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;

import com.example.skyfast_2_0.entity.*;

import com.example.skyfast_2_0.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TicketService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private PassengerRepository passengerRepository;

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
    public TicketInfoDTO createTicket(TicketInfoDTO ticketInfoDTO) {
        if(ticketInfoDTO == null || ticketInfoDTO.getBookingId() == null  || ticketInfoDTO.getFlightNumber() == null || ticketInfoDTO.getSeatNumber() == null || ticketInfoDTO.getPassengerFullName() == null) {
            throw new IllegalArgumentException("Ticket data must not be null or emty");
        }
        // Lấy booking từ database
        Booking booking = bookingRepository.findById(ticketInfoDTO.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + ticketInfoDTO.getBookingId()));
        // Lấy flight từ database
        Flight flight = flightRepository.findByFlightNumber(ticketInfoDTO.getFlightNumber())
                .orElseThrow(() -> new RuntimeException("Flight not found with flightNumber: " + ticketInfoDTO.getFlightNumber()));
        // Lấy seat từ database
        Seat seat = seatRepository.findBySeatNumber(ticketInfoDTO.getSeatNumber())
                .orElseThrow(() -> new RuntimeException("Seat not found with seatNumber: " + ticketInfoDTO.getSeatNumber()));
        // Lấy passenger từ database
        Passenger passenger = passengerRepository.findByFullName(ticketInfoDTO.getPassengerFullName())
                .orElseThrow(() -> new RuntimeException("Passenger not found with fullName: " + ticketInfoDTO.getPassengerFullName()));

        Ticket ticket = new Ticket();
        ticket.setStatus(ticketInfoDTO.getStatus());
        ticket.setTicketPrice(ticketInfoDTO.getTicketPrice());
        ticket.setBooking(booking);
        ticket.setFlight(flight);
        ticket.setSeat(seat);
        ticket.setPassenger(passenger);
        ticket = ticketRepository.save(ticket);
        return new TicketInfoDTO(ticket);

    }
}
