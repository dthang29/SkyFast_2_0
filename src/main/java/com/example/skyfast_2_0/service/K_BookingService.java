package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.K_BookingDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;
import com.example.skyfast_2_0.entity.Ticket;

import com.example.skyfast_2_0.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.example.skyfast_2_0.entity.Booking;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class K_BookingService {
    @Autowired
    private K_TicketRepository KTicketRepository;

    @Autowired
    private K_UserRepository KUserRepository;

    @Autowired
    private K_PromotionRepository KPromotionRepository;

    @Autowired
    private T_UserRepository userRepository;

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
                        booking.getUser().getUserName(),
                        booking.getPromotion() != null ? booking.getPromotion().getId() : null,
                        booking.getPromotion() != null ? booking.getPromotion().getCode() : null,
                        booking.getBookingCode()))
                .collect(Collectors.toList());
    }

    public K_BookingDTO getBookingById(Integer id) {
        Booking booking = KBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        Integer promotionId = (booking.getPromotion() != null) ? booking.getPromotion().getId() : null;
        String  promotionCode = (booking.getPromotion() != null) ? booking.getPromotion().getCode() : null;
        return new K_BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(), booking.getBookingStatus(),
                userId, booking.getUser().getUserName(), promotionId, promotionCode, booking.getBookingCode());
    }

    public K_BookingDTO updateBookingStatus(Integer id, String bookingStatus) {
        Booking booking = KBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.setBookingStatus(bookingStatus);
        KBookingRepository.save(booking);
        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        Integer promotionId = (booking.getPromotion() != null) ? booking.getPromotion().getId() : null;
        String promotionCode= (booking.getPromotion() != null) ? booking.getPromotion().getCode() : null;
        return new K_BookingDTO(booking.getId(), booking.getTotalPrice(), booking.getBookingDate(),
                booking.getBookingStatus(), userId, booking.getUser().getUserName(),
                promotionId, promotionCode, booking.getBookingCode());
    }

    public List<K_TicketInfoDTO> getTicketsByBookingId(Integer bookingId) {
        List<Ticket> tickets = KTicketRepository.findByBookingId(bookingId);
        return tickets.stream().map(K_TicketInfoDTO::new).collect(Collectors.toList());
    }

    public List<K_BookingDTO> getBookingHistoryByUserId(Integer userId) {
        return KBookingRepository.findAll().stream()
                .filter(booking -> booking.getUser() != null
                        && booking.getUser().getId().equals(userId)
                        && !"Cancelled".equals(booking.getBookingStatus()))
                .map(booking -> new K_BookingDTO(
                        booking.getId(),
                        booking.getTotalPrice(),
                        booking.getBookingDate(),
                        booking.getBookingStatus(),
                        booking.getUser().getId(),
                        booking.getUser().getUserName(),
                        booking.getPromotion() != null ? booking.getPromotion().getId() : null,
                        booking.getPromotion() != null ? booking.getPromotion().getCode() : null,
                        booking.getBookingCode()))
                .collect(Collectors.toList());
    }

    public K_BookingDTO cancelBookingById(Integer id) {
        Booking booking = KBookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        booking.setBookingStatus("Cancelled"); // Đặt trạng thái thành CANCELLED
        KBookingRepository.save(booking); // Lưu lại thay đổi

        Integer userId = (booking.getUser() != null) ? booking.getUser().getId() : null;
        Integer promotionId = (booking.getPromotion() != null) ? booking.getPromotion().getId() : null;
        String promotionCode = (booking.getPromotion() != null) ? booking.getPromotion().getCode() : null;

        return new K_BookingDTO(
                booking.getId(),
                booking.getTotalPrice(),
                booking.getBookingDate(),
                booking.getBookingStatus(),
                userId,
                booking.getUser().getUserName(),
                promotionId,
                promotionCode,
                booking.getBookingCode()
        );
    }


   public String getUserEmail(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        } else if (authentication.getPrincipal() instanceof OAuth2User) {
            Map<String, Object> attributes = ((OAuth2User) authentication.getPrincipal()).getAttributes();
            return (String) attributes.get("email");
        }
        return null;
    }

}