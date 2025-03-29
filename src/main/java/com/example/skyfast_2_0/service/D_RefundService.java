package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Refund;
import com.example.skyfast_2_0.repository.D_RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class D_RefundService {

    @Autowired
    private D_RefundRepository DRefundRepository;

    //    public List<Refund> getAllRefunds() {
//        return refundRepository.findAll();
//    }
    public List<Refund> getAllRefunds() {
        List<Refund> refundList = DRefundRepository.findAll();
        DecimalFormat df = new DecimalFormat("#.#");  // Định dạng chỉ hiển thị 1 chữ số sau dấu phẩy

        // Cập nhật refundPrice = booking.totalPrice * 80% (nếu bạn muốn tính lại mỗi lần hiển thị)
        for (Refund r : refundList) {
            float totalPrice = r.getBooking().getTotalPrice(); // Giả sử booking có hàm getTotalPrice()
            float newRefundPrice = totalPrice * 0.8f;
            // Định dạng refundPrice để chỉ hiển thị 1 chữ số sau dấu phẩy
            String formattedRefundPrice = df.format(newRefundPrice);
            r.setRefundPrice(Float.parseFloat(formattedRefundPrice));
        }
        return refundList;
    }
    public Optional<Refund> getRefundById(Integer id) {
        return DRefundRepository.findById(id);
    }

    public Refund updateRefund(Integer id, String status, LocalDateTime refundDate) {
        Optional<Refund> refundOpt = DRefundRepository.findById(id);
        if (refundOpt.isPresent()) {
            Refund refund = refundOpt.get();
            refund.setStatus(status);
            refund.setRefundDate(refundDate);
            return DRefundRepository.save(refund);
        }
        return null;
    }

    public List<Refund> searchRefunds(String status, LocalDateTime fromRequestDate, LocalDateTime toRequestDate, LocalDateTime fromRefundDate, LocalDateTime toRefundDate) {
        return DRefundRepository.searchRefunds(status, fromRequestDate, toRequestDate, fromRefundDate, toRefundDate);
    }

    public boolean updateRefundStatus(Integer id, String newStatus, String reason) {
        Optional<Refund> refundOpt = DRefundRepository.findById(id);
        if (refundOpt.isPresent()) {
            Refund refund = refundOpt.get();
            // Chỉ cho phép cập nhật nếu trạng thái hiện tại là "Processing"
            if (!refund.getStatus().equalsIgnoreCase("Processing")) {
                return false;
            }
            // Chỉ chấp nhận trạng thái mới là Approve hoặc Reject
            if (!(newStatus.equalsIgnoreCase("Approve") || newStatus.equalsIgnoreCase("Reject"))) {
                return false;
            }
            refund.setStatus(newStatus);
            refund.setResponse(reason);
            refund.setRefundDate(LocalDateTime.now()); // Ghi nhận thời điểm cập nhật
            DRefundRepository.save(refund);
            return true;
        }
        return false;
    }

}
