package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.BookingDTO;
import com.example.skyfast_2_0.dto.TicketDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.TicketRepository;
import com.example.skyfast_2_0.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.skyfast_2_0.repository.BookingRepository;
import com.example.skyfast_2_0.entity.Booking;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.stream.Collectors;
@Service
public class BookingService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<BookingDTO> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(booking -> new BookingDTO(
                        booking.getId(),
                        booking.getTotalPrice(),
                        booking.getBookingDate(),
                        booking.getBookingStatus(),
                        booking.getUser() != null ? booking.getUser().getId() : null,
                        booking.getUser().getUserName()))
                .collect(Collectors.toList());
    }

    public BookingDTO getBookingById(Integer id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        return new BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(), booking.getBookingStatus(), userId, booking.getUser().getUserName());
    }

    public BookingDTO updateBookingStatus(Integer id, String bookingStatus) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setBookingStatus(bookingStatus);
        bookingRepository.save(booking);
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        return new BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(), booking.getBookingStatus(), userId, booking.getUser().getUserName());
    }

    public List<TicketInfoDTO> getTicketsByBookingId(Integer bookingId) {
        List<Ticket> tickets = ticketRepository.findByBookingId(bookingId);
        return tickets.stream().map(TicketInfoDTO::new).collect(Collectors.toList());
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        if (bookingDTO == null || bookingDTO.getUserName() == null || bookingDTO.getUserName().isEmpty()) {
            throw new IllegalArgumentException("Booking data or userId must not be null or emty");
        }
        // Lấy user từ database
        User user = userRepository.findByUserName(bookingDTO.getUserName())
                .orElseThrow(() -> new RuntimeException("User not found with username: " + bookingDTO.getUserName()));
        Booking booking = new Booking();
        booking.setTotalPrice(bookingDTO.getTotalPrice());
        booking.setBookingDate(bookingDTO.getBookingDate());
        booking.setBookingStatus(bookingDTO.getBookingStatus());
        booking.setUser(user);
        booking = bookingRepository.save(booking);
        return new BookingDTO(booking.getId(),
                booking.getTotalPrice(),
                booking.getBookingDate(),
                booking.getBookingStatus(),
                booking.getUser().getId(),
                booking.getUser().getUserName());
    }
}