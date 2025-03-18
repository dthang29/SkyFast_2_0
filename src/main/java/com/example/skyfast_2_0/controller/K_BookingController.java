package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.K_BookingDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;
import com.example.skyfast_2_0.service.K_BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class K_BookingController {
    private final K_BookingService KBookingService;

    public K_BookingController(K_BookingService KBookingService) {
        this.KBookingService = KBookingService;
    }

    @GetMapping
    public String getAllBookings(Model model) {
        List<K_BookingDTO> bookings = KBookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookingManagement";
    }

    @GetMapping("/{id}")
    public String getBookingById(@PathVariable Integer id, Model model) {
        K_BookingDTO booking = KBookingService.getBookingById(id);
        if (booking == null) {
            return "notFound";
        }
        model.addAttribute("booking", booking);
        return "bookingDetail";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Integer id, @ModelAttribute K_BookingDTO KBookingDTO) {
        K_BookingDTO updatedBooking = KBookingService.updateBookingStatus(id, KBookingDTO.getBookingStatus());
        if(updatedBooking == null) {
            return "notFound";
        }
        return "redirect:/bookings";
    }



    @GetMapping("/{id}/tickets")
    public String getTicketsByBookingId(@PathVariable Integer id, Model model) {
        List<K_TicketInfoDTO> tickets = KBookingService.getTicketsByBookingId(id);
        if (tickets == null || tickets.isEmpty()) {
            return "notFound"; // Trả về trang notFound nếu không tìm thấy vé
    }
        model.addAttribute("tickets", tickets);
        model.addAttribute("filterBookingId", id);  // Truyền Booking ID vào model để lọc
        return "ticketManagement"; // Trả về trang quản lý vé thay vì redirect
    }
}
