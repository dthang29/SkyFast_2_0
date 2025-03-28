package com.example.skyfast_2_0.controller;


import com.example.skyfast_2_0.dto.K_BookingDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.K_BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/homepage")
public class BookingUserController {
    private final K_BookingService KBookingService;

    public BookingUserController(K_BookingService KBookingService) {
        this.KBookingService = KBookingService;
    }

    @Autowired
    private T_UserRepository userRepository;

    @GetMapping("/booking-history")
    public String getBookingHistoryByUserId(Model model, Authentication authentication) {
        String email = KBookingService.getUserEmail(authentication);
        User user = userRepository.findByEmail(email);
        Integer userId = user.getId();
        List<K_BookingDTO> bookings = KBookingService.getBookingHistoryByUserId(userId);
        model.addAttribute("bookings", bookings);
        return "BookingHistory";
    }

    @GetMapping("/{id}/tickets")
    public String getTicketsByBookingId(@PathVariable Integer id, Model model) {
        List<K_TicketInfoDTO> tickets = KBookingService.getTicketsByBookingId(id);
        model.addAttribute("tickets", tickets);
        model.addAttribute("filterBookingId", id);  // Truyền Booking ID vào model để lọc
        return "TicketHistory"; // Trả về trang quản lý vé thay vì redirect
    }

    @PostMapping("/cancel-booking/{id}")
    public String cancelBooking(@PathVariable Integer id, Authentication authentication) {
        String email = KBookingService.getUserEmail(authentication);
        User user = userRepository.findByEmail(email);

        // Kiểm tra xem booking có thuộc về user này không
        K_BookingDTO booking = KBookingService.getBookingById(id);
        if (booking.getUserId() != null && booking.getUserId().equals(user.getId())) {
            KBookingService.cancelBookingById(id);
        }

        return "redirect:/homepage/booking-history"; // Quay lại danh sách booking
    }
}
