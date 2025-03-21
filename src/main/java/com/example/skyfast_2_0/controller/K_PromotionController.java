package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.K_PromotionDTO;
import com.example.skyfast_2_0.service.K_PromotionService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import java.util.List;

@Controller
@RequestMapping("/admin/promotions")
public class K_PromotionController {
    private final K_PromotionService KPromotionService;

    public K_PromotionController(K_PromotionService KPromotionService) {this.KPromotionService = KPromotionService;}

    @GetMapping
    public String getAllPromotions(Model model) {
        List<K_PromotionDTO> promotions = KPromotionService.getAllPromotions();
        model.addAttribute("promotions", promotions);
        return "promotionManagement";
    }

    @GetMapping("/{id}")
    public String getPromotionById(@PathVariable Integer id, Model model) {
        K_PromotionDTO promotion = KPromotionService.getPromotionById(id);
        if (promotion == null) {
            return "notFound";
        }
        model.addAttribute("promotion", promotion);
        return "promotionDetail";
    }

    @PostMapping("/update/{id}")
    public String updatePromotion(@PathVariable Integer id, @ModelAttribute K_PromotionDTO KPromotionDTO,  Model model) {
        try {
            K_PromotionDTO updatedPromotion = KPromotionService.updatePromotionStatus(id, KPromotionDTO.getEndDate(), KPromotionDTO.getStatus());
            if (updatedPromotion == null) {
                return "notFound";
            }
            model.addAttribute("message", "Updated promotion successfully");
            return "redirect:/admin/promotions";
        } catch(Exception e){
                model.addAttribute("error", "Error: " + e.getMessage());
                model.addAttribute("promotion", KPromotionDTO);
                return "promotionDetail";
            }
        }


// hiển thị tạo mới promotion
    @GetMapping("/new")
    public String newPromotion(Model model) {
        model.addAttribute("promotion", new K_PromotionDTO());
        return "promotionCreate";
    }
//    tạo mới promotion
    @PostMapping("/create")
    public String createPromotion(@ModelAttribute K_PromotionDTO KPromotionDTO, Model model) {
        try{
            K_PromotionDTO createdPromotion = KPromotionService.createPromotion(KPromotionDTO);
            model.addAttribute("message", "Created promotion successfully"); // Thêm thông báo vào model
            model.addAttribute("promotion", new K_PromotionDTO()); //reset form
            return "promotionCreate";  // Trả về trang tạo mới promotion
        } catch (Exception e) {
            model.addAttribute("error", "Error: " + e.getMessage()); // Thêm thông báo lỗi vào model
            model.addAttribute("promotion", new K_PromotionDTO()); //reset form
            return "promotionCreate";  // Trả về trang tạo mới promotion
        }
    }
}
