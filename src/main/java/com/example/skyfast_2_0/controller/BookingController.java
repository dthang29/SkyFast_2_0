package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.BookingDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;
import com.example.skyfast_2_0.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    public String getAllBookings(Model model) {
        List<BookingDTO> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "bookingManagement";
    }

    @GetMapping("/{id}")
    public String getBookingById(@PathVariable Integer id, Model model) {
        BookingDTO booking = bookingService.getBookingById(id);
        if (booking == null) {
            return "notFound";
        }
        model.addAttribute("booking", booking);
        return "bookingDetail";
    }

    @PostMapping("/update/{id}")
    public String updateBooking(@PathVariable Integer id, @ModelAttribute BookingDTO bookingDTO) {
        BookingDTO updatedBooking = bookingService.updateBookingStatus(id, bookingDTO.getBookingStatus());
        if(updatedBooking == null) {
            return "notFound";
        }
        return "redirect:/bookings";
    }

//    hiển thị tạo mới booking
    @GetMapping("/new")
     public String newBooking(Model model) {
        model.addAttribute("booking", new BookingDTO());
        return "bookingCreate"; // Trả về view bookingCreate.html
    }

// Tạo mới booking
    @PostMapping("/create")
    public ResponseEntity<?> createBooking(@ModelAttribute BookingDTO bookingDTO) {
         try{
             BookingDTO createdBooking = bookingService.createBooking(bookingDTO);
             return ResponseEntity.ok(createdBooking);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Failed to create promotion" + e.getMessage());
         }
    }

    @GetMapping("/{id}/tickets")
    public String getTicketsByBookingId(@PathVariable Integer id, Model model) {
        List<TicketInfoDTO> tickets = bookingService.getTicketsByBookingId(id);
        if (tickets == null || tickets.isEmpty()) {
            return "notFound"; // Trả về trang notFound nếu không tìm thấy vé
        }
        model.addAttribute("tickets", tickets);
        model.addAttribute("filterBookingId", id);  // Truyền Booking ID vào model để lọc
        return "ticketManagement"; // Trả về trang quản lý vé thay vì redirect
    }


}
