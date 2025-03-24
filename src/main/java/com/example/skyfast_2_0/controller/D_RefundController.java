package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Refund;
import com.example.skyfast_2_0.service.D_RefundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/staff/refund")
public class D_RefundController {

    @Autowired
    private D_RefundService DRefundService;

    @GetMapping("/list")
    public String getRefundList(Model model) {
        List<Refund> refundList = DRefundService.getAllRefunds();
        model.addAttribute("refundList", refundList);
        return "refundList";
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Optional<Refund> getRefundById(@PathVariable Integer id) {
        return DRefundService.getRefundById(id);
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public Map<String, Object> updateRefund(@PathVariable Integer id, @RequestBody Map<String, Object> requestData) {
        String status = (String) requestData.get("status");
        String refundDateString = (String) requestData.get("refundDate");
        LocalDateTime refundDate = refundDateString != null ? LocalDateTime.parse(refundDateString) : null;

        Refund updatedRefund = DRefundService.updateRefund(id, status, refundDate);
        Map<String, Object> response = new HashMap<>();
        response.put("success", updatedRefund != null);
        return response;
    }

    //    @PostMapping("/updateStatus/{id}")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> updateRefundStatus(
//            @PathVariable Integer id,
//            @RequestBody Map<String, String> requestData) {
//
//        String newStatus = requestData.get("status");
//        boolean isUpdated = refundService.updateRefundStatus(id, newStatus);
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("success", isUpdated);
//        return ResponseEntity.ok(response);
//    }
    @PostMapping("/updateStatus/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateRefundStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestData) {

        String newStatus = requestData.get("status");
        String responsed = requestData.get("reason");
        boolean isUpdated = DRefundService.updateRefundStatus(id, newStatus, responsed);

        Map<String, Object> response = new HashMap<>();
        response.put("success", isUpdated);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Refund>> searchRefunds(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromRequestDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toRequestDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromRefundDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toRefundDate) {

        List<Refund> refunds = DRefundService.searchRefunds(
                status,
                fromRequestDate != null ? fromRequestDate.atStartOfDay() : null,
                toRequestDate != null ? toRequestDate.atTime(23, 59, 59) : null,
                fromRefundDate != null ? fromRefundDate.atStartOfDay() : null,
                toRefundDate != null ? toRefundDate.atTime(23, 59, 59) : null
        );
        for (Refund r : refunds) {
            float totalPrice = r.getBooking().getTotalPrice();
            r.setRefundPrice(totalPrice * 0.8f);
        }

        return ResponseEntity.ok(refunds);
    }

}
