package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.repository.BookingRepository;
import com.example.skyfast_2_0.repository.PromotionRepository;
import com.example.skyfast_2_0.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    private PromotionRepository promotionRepository;
    @Autowired
    private UserRepository userRepository;
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        private static final SecureRandom RANDOM = new SecureRandom();

        public static String generateBookingCode(int length) {
            StringBuilder sb = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                sb.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
            }
            return sb.toString();
        }
        @Transactional
    public void insertBooking(float totalPrice, String bookingStatus, Integer userId, Integer promotionId) {
        String bookingCode = generateBookingCode(8);
        bookingRepository.insertBooking(totalPrice, LocalDateTime.now(), bookingStatus, userRepository.findUserById(userId), promotionRepository.findPromotionById(promotionId), bookingCode);
    }
    public Booking findBookingWithMaxId() {
            return bookingRepository.findBookingWithMaxId();
    }
}
