package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Feedback;
import com.example.skyfast_2_0.service.D_FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/staff/feedback")
public class D_FeedbackController {

    @Autowired
    private D_FeedbackService DFeedbackService;

    // Hiển thị trang feedbackList (Thymeleaf)
    @GetMapping("/list")
    public String getFeedbackList(Model model) {
        List<Feedback> feedbackList = DFeedbackService.getAllFeedback();
        model.addAttribute("feedbackList", feedbackList);
        return "feedbackList"; // Tên file HTML (VD: feedbackList.html)
    }

    // Search trả về JSON (để JavaScript cập nhật bảng)
    @GetMapping("/search")
    @ResponseBody
    public List<Feedback> searchFeedback(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String flightCode,
            @RequestParam(required = false) Integer rating
    ) {
        return DFeedbackService.searchFeedback(username, flightCode, rating);
    }
}
