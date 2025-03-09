package com.example.skyfast_2_0.controller;


import com.example.skyfast_2_0.dto.K_TicketDTO;
import com.example.skyfast_2_0.dto.K_TicketInfoDTO;
import com.example.skyfast_2_0.service.K_TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class K_TicketController {

    private final K_TicketService KTicketService;

    public K_TicketController(K_TicketService KTicketService) {
        this.KTicketService = KTicketService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        List<K_TicketInfoDTO> tickets = KTicketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketManagement"; // Trả về trang Thymeleaf hiển thị danh sách vé
    }

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable Integer id, Model model) {
        K_TicketInfoDTO ticket = KTicketService.getTicketById(id);
        if (ticket == null) {
            return "error/404"; // Trả về trang lỗi nếu không tìm thấy
        }
        model.addAttribute("ticket", ticket);
        return "ticketDetail"; // Trả về trang chi tiết vé
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable Integer id, @ModelAttribute K_TicketDTO KTicketDTO) {
        K_TicketInfoDTO updatedTicket = KTicketService.updateTicket(id, KTicketDTO);
        if (updatedTicket == null) {
            return "error/404";
        }
        return "redirect:/tickets"; // Chuyển hướng về danh sách sau khi cập nhật
    }

    @GetMapping("/new")
    public String newTicket(Model model) {
        model.addAttribute("ticket", new K_TicketInfoDTO());
        return "ticketCreate"; // Trả về view ticketCreate.html
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute K_TicketInfoDTO KTicketInfoDTO, Model model) {
        try {
            K_TicketInfoDTO createdTicket = KTicketService.createTicket(KTicketInfoDTO);
            model.addAttribute("message", "Create successful!"); // Thêm thông báo vào model
            model.addAttribute("ticket", new K_TicketInfoDTO()); // Reset form
            return "ticketCreate"; // Giữ nguyên trang tạo booking
        } catch (Exception e) {
            model.addAttribute("error", "Failed to create ticket: " + e.getMessage());
            model.addAttribute("ticket", new K_TicketInfoDTO());
            return "ticketCreate";
        }
    }

}

