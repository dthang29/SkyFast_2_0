package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Refund;
import com.example.skyfast_2_0.repository.D_RefundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class D_RefundService {

    @Autowired
    private D_RefundRepository DRefundRepository;

    public List<Refund> getAllRefunds() {
        return DRefundRepository.findAll();
    }

    public Optional<Refund> getRefundById(Integer id) {
        return DRefundRepository.findById(id);
    }

    public Refund updateRefund(Integer id, String status, LocalDate refundDate) {
        Optional<Refund> refundOpt = DRefundRepository.findById(id);
        if (refundOpt.isPresent()) {
            Refund refund = refundOpt.get();
            refund.setStatus(status);
            refund.setRefundDate(refundDate);
            return DRefundRepository.save(refund);
        }
        return null;
    }

    public List<Refund> searchRefunds(String status, LocalDate fromRequestDate, LocalDate toRequestDate, LocalDate fromRefundDate, LocalDate toRefundDate) {
        return DRefundRepository.searchRefunds(status, fromRequestDate, toRequestDate, fromRefundDate, toRefundDate);
    }

    public boolean updateRefundStatus(Integer id, String newStatus) {
        Optional<Refund> refundOpt = DRefundRepository.findById(id);

        if (refundOpt.isPresent()) {
            Refund refund = refundOpt.get();

            // Kiểm tra nếu trạng thái hợp lệ
            if ((refund.getStatus().equals("Unprocessed") && newStatus.equals("Is_Processing")) ||
                    (refund.getStatus().equals("Is_Processing") && newStatus.equals("Processed"))) {

                refund.setStatus(newStatus);
                DRefundRepository.save(refund);
                return true;
            }
        }
        return false;
    }
}
