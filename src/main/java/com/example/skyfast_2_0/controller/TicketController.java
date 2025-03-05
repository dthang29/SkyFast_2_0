package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.TicketDTO;
import com.example.skyfast_2_0.dto.TicketInfoDTO;
import com.example.skyfast_2_0.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String getAllTickets(Model model) {
        List<TicketInfoDTO> tickets = ticketService.getAllTickets();
        model.addAttribute("tickets", tickets);
        return "ticketManagement"; // Trả về trang Thymeleaf hiển thị danh sách vé
    }

    @GetMapping("/{id}")
    public String getTicketById(@PathVariable Integer id, Model model) {
        TicketInfoDTO ticket = ticketService.getTicketById(id);
        if (ticket == null) {
            return "error/404"; // Trả về trang lỗi nếu không tìm thấy
        }
        model.addAttribute("ticket", ticket);
        return "ticketDetail"; // Trả về trang chi tiết vé
    }

    @PostMapping("/update/{id}")
    public String updateTicket(@PathVariable Integer id, @ModelAttribute TicketDTO ticketDTO) {
        TicketInfoDTO updatedTicket = ticketService.updateTicket(id, ticketDTO);
        if (updatedTicket == null) {
            return "error/404";
        }
        return "redirect:/tickets"; // Chuyển hướng về danh sách sau khi cập nhật
    }

    @GetMapping("/new")
    public String newTicket(Model model) {
        model.addAttribute("ticket", new TicketInfoDTO());
        return "ticketCreate"; // Trả về view ticketCreate.html
    }

    @PostMapping("/create")
    public String createTicket(@ModelAttribute TicketDTO ticketDTO) {
        ticketService.createTicket(ticketDTO);
        return "redirect:/tickets"; // Chuyển hướng về danh sách sau khi tạo mới
    }
}

