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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/refund")
public class D_RefundController {

    @Autowired
    private D_RefundService DRefundService;

    @GetMapping("/list")
    public String getRefundList(Model model) {
        List<Refund> refundList = DRefundService.getAllRefunds();
        model.addAttribute("refundList", refundList);
        return "refundList"; // Trả về trang Thymeleaf
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
        LocalDate refundDate = LocalDate.parse((String) requestData.get("refundDate"));

        Refund updatedRefund = DRefundService.updateRefund(id, status, refundDate);
        Map<String, Object> response = new HashMap<>();
        response.put("success", updatedRefund != null);
        return response;
    }

    @PostMapping("/updateStatus/{id}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateRefundStatus(
            @PathVariable Integer id,
            @RequestBody Map<String, String> requestData) {

        String newStatus = requestData.get("status");
        boolean isUpdated = DRefundService.updateRefundStatus(id, newStatus);

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

        List<Refund> refunds = DRefundService.searchRefunds(status, fromRequestDate, toRequestDate, fromRefundDate, toRefundDate);
        return ResponseEntity.ok(refunds);
    }
}
