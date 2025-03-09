package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.L_UserDTO;
import com.example.skyfast_2_0.service.L_UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/users")
public class L_UserController {
    private final L_UserService LUserService;

    public L_UserController(L_UserService LUserService) {
        this.LUserService = LUserService;
    }

    @GetMapping("/list")
    public String getAllUsers(Model model) {
        List<L_UserDTO> users = LUserService.getAllActiveAndInactiveUsers();
        model.addAttribute("users", users);
        return "userlist";
    }

    @GetMapping("/detail/{id}")
    public String getUserDetail(@PathVariable Integer id, Model model) {
        L_UserDTO user = LUserService.getUserById(id);
        if (user != null) {
            model.addAttribute("user", user);
            return "UserDetail";
        }
        return "redirect:/users/list";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute L_UserDTO LUserDTO, RedirectAttributes redirectAttributes) {
        try {
            LUserService.createUser(LUserDTO);
            redirectAttributes.addFlashAttribute("successMessage", "User created successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to create user: " + e.getMessage());
        }
        return "redirect:/users/list";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Integer id, @ModelAttribute L_UserDTO LUserDTO, RedirectAttributes redirectAttributes) {
        try {
            L_UserDTO updatedUser = LUserService.updateUser(id, LUserDTO);
            if (updatedUser != null) {
                redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update user: " + e.getMessage());
        }
        return "redirect:/users/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            if (LUserService.deleteUser(id)) {
                redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "User not found!");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete user: " + e.getMessage());
        }
        return "redirect:/users/list";
    }
}
