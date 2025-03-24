package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Feedback;
import com.example.skyfast_2_0.repository.D_FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class D_FeedbackService {

    @Autowired
    private D_FeedbackRepository DFeedbackRepository;

    // Lấy tất cả feedback
    public List<Feedback> getAllFeedback() {
        return DFeedbackRepository.findAll();
    }

    // Tìm kiếm feedback dựa trên các tiêu chí
    public List<Feedback> searchFeedback(String username, String flightCode, Integer rating) {
        return DFeedbackRepository.searchFeedback(username, flightCode, rating);
    }
}
