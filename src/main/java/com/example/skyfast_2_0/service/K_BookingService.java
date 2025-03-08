package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.K_BookingDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.K_BookingRepository;
import com.example.skyfast_2_0.repository.K_TicketRepository;
import com.example.skyfast_2_0.repository.U_UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.skyfast_2_0.entity.Booking;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class K_BookingService {
    @Autowired
    private K_TicketRepository KTicketRepository;
    @Autowired
    private U_UserRepository UUserRepository;

    private final K_BookingRepository KBookingRepository;

    public K_BookingService(K_BookingRepository KBookingRepository) {
        this.KBookingRepository = KBookingRepository;
    }

    public List<K_BookingDTO> getAllBookings() {
        return KBookingRepository.findAll().stream()
                .map(booking -> new K_BookingDTO(
                        booking.getId(),
                        booking.getTotalPrice(),
                        booking.getBookingDate(),
                        booking.getBookingStatus(),
                        booking.getUser() != null ? booking.getUser().getId() : null,
                        booking.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    public K_BookingDTO getBookingById(Integer id) {
        Booking booking = KBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        return new K_BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(), booking.getBookingStatus(), userId, booking.getUser().getUserName());
    }

    public K_BookingDTO updateBookingStatus(Integer id, String bookingStatus) {
        Booking booking = KBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setBookingStatus(bookingStatus);
        KBookingRepository.save(booking);
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        return new K_BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(), booking.getBookingStatus(), userId, booking.getUser().getUserName());
    }

    public List<K_TicketInfoDTO> getTicketsByBookingId(Integer bookingId) {
        List<Ticket> tickets = KTicketRepository.findByBookingId(bookingId);
        return tickets.stream().map(K_TicketInfoDTO::new).collect(Collectors.toList());
    }

    public K_BookingDTO createBooking(K_BookingDTO KBookingDTO) {
        if (KBookingDTO == null || KBookingDTO.getUserName() == null || KBookingDTO.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Booking data or userId must not be null or emty");
        }
        // Lấy user từ database
        User user = UUserRepository.findByUserName(KBookingDTO.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + KBookingDTO.getUserName()));
        Booking booking = new Booking();
        booking.setTotalPrice(KBookingDTO.getTotalPrice());
        booking.setBookingDate(KBookingDTO.getBookingDate());
        booking.setBookingStatus(KBookingDTO.getBookingStatus());
        booking.setUser(user);
        booking = KBookingRepository.save(booking);
        return new K_BookingDTO(booking.getId(),
                booking.getTotalPrice(),
                booking.getBookingDate(),
                booking.getBookingStatus(),
                booking.getUser().getId(),
                booking.getUser().getUserName());
    }
}