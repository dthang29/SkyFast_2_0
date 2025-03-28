package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.K_BookingDTO;
import com.example.skyfast_2_0.entity.Booking;
import com.example.skyfast_2_0.entity.Refund;
import com.example.skyfast_2_0.repository.K_BookingRepository;
import com.example.skyfast_2_0.repository.RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RefundService {

    @Autowired
    private K_BookingRepository KBookingRepository;

    private final RefundRepository refundRepository;

    public RefundService(RefundRepository refundRepository) {
        this.refundRepository = refundRepository;
    }
    public String RequestRefund(String bookingCode, String reason, String bank, String bankNumber) {
        // Check if bookingId exists
        var optionalBooking = KBookingRepository.findByBookingCode(bookingCode);
        if (optionalBooking.isEmpty()) {
            return "Not found booking code!";
        }
        if (reason == null || reason.isBlank()) return "Lỗi: Lý do hoàn tiền không được để trống!";
        if (bank == null || bank.isBlank()) return "Lỗi: Ngân hàng không được để trống!";
        if (bankNumber == null || bankNumber.isBlank()) return "Lỗi: Số tài khoản không được để trống!";

        Booking booking = optionalBooking.get();
        // Kiểm tra totalPrice
        if (booking.getTotalPrice() == null) {
            return " Booking not have totalPrice!";
        }
        // Check if refund exist before
        if (refundRepository.existsByBooking(booking)) {
            return "Refund request has been sent before";
        }
        // Check if booking is eligible for refund
        if (booking.getBookingStatus().equals("PROCESSING") || booking.getBookingStatus().equals("Cancelled")) {
            return "Booking is not eligible for refund";
        }
        Float refundPrice = booking.getTotalPrice() * 0.8f;
        // Create refund request
        Refund refund = new Refund();
        refund.setBooking(booking);
        refund.setStatus("PROCESSING");
        refund.setBank(bank);
        refund.setBankNumber(bankNumber);
        refund.setRefundPrice(refundPrice);
        refund.setRequestDate(LocalDateTime.now());
        refund.setReason(reason);
        refundRepository.save(refund);
        return "Refund request has been sent";
    }
    public List<K_BookingDTO> getRefundsByUserId(Integer userId) {
        List<Refund> refunds = refundRepository.findAllByBooking_User_Id(userId);

        return refunds.stream().map(refund -> new K_BookingDTO(
                refund.getBooking().getId(),
                refund.getRefundPrice(), // Tổng tiền refund
                refund.getRequestDate(), // Ngày yêu cầu refund
                refund.getStatus(), // Trạng thái refund
                userId,
                refund.getBooking().getUser().getUserName(),
                null,
                null,
                refund.getBooking().getBookingCode()
        )).collect(Collectors.toList());
    }

}
