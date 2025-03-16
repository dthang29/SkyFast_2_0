package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.D_MaintenanceDTO;
import com.example.skyfast_2_0.entity.Maintenance;
import com.example.skyfast_2_0.repository.D_PlaneCategoryRepository;
import com.example.skyfast_2_0.service.D_MaintenanceService;
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
@RequestMapping("/maintenance")
public class D_MaintenanceController {

    @Autowired
    private D_MaintenanceService DMaintenanceService;

    @Autowired
    private D_PlaneCategoryRepository airplaneRepository;

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addMaintenance(@RequestBody D_MaintenanceDTO request) {
        boolean success = DMaintenanceService.addMaintenance(request);
        Map<String, Object> response = new HashMap<>();
        response.put("success", success);
        if (success) {
            response.put("redirectUrl", "/maintenance/list/" + request.getAirplaneId());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list/{airplaneId}")
    public String getMaintenanceList(@PathVariable Integer airplaneId, Model model) {
        List<Maintenance> maintenanceList = DMaintenanceService.getMaintenanceByAirplaneId(airplaneId);
        model.addAttribute("maintenanceList", maintenanceList);
        model.addAttribute("airplane", airplaneRepository.findById(airplaneId).orElse(null));

        return "maintenanceList"; // Trả về trang Thymeleaf (Không phải JSON)
    }

    @GetMapping("/list")
    public String getAllMaintenanceList(Model model) {
        List<Maintenance> maintenanceList = DMaintenanceService.getAllMaintenanceList();
        model.addAttribute("maintenanceList", maintenanceList);
//        model.addAttribute("airplane", airplaneRepository.findById(airplaneId).orElse(null));

        return "maintenanceListAll"; // Trả về trang Thymeleaf (Không phải JSON)
    }

    @GetMapping("/get/{id}")
    @ResponseBody
    public Optional<Maintenance> getMaintenanceById(@PathVariable Integer id) {
        return DMaintenanceService.getMaintenanceById(id);
    }

    @PostMapping("/update/{id}")
    @ResponseBody
    public Map<String, Object> updateMaintenance(@PathVariable Integer id, @RequestBody Map<String, Object> requestData) {
        String status = (String) requestData.get("maintenanceStatus");
        String description = (String) requestData.get("description");
        int duration = Integer.parseInt((String) requestData.get("duration"));
        Maintenance updatedMaintenance = DMaintenanceService.updateMaintenance(id, status, description, duration);

        Map<String, Object> response = new HashMap<>();
        if (updatedMaintenance != null) {
            response.put("success", true);
            response.put("message", "Maintenance updated successfully!");
        } else {
            response.put("success", false);
            response.put("message", "Failed to update maintenance!");
        }
        return response;
    }

    @GetMapping("/search")
    @ResponseBody
    public List<Maintenance> searchMaintenance(
            @RequestParam(required = false) String airplaneId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        Integer airplaneIdd = Integer.parseInt(airplaneId);
        return DMaintenanceService.searchMaintenance(airplaneIdd,status, fromDate, toDate);
    }

    @GetMapping("/list/json/{airplaneId}")
    @ResponseBody
    public List<Maintenance> getMaintenanceListJson(@PathVariable Integer airplaneId) {
        return DMaintenanceService.getMaintenanceByAirplaneId(airplaneId);
    }
    @GetMapping("/list/json")
    @ResponseBody
    public List<Maintenance> getAllMaintenanceListJson() {
        return DMaintenanceService.getAllMaintenanceList();
    }

    @GetMapping("/searchAll")
    @ResponseBody
    public List<Maintenance> searchMaintenance(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {

        return DMaintenanceService.searchMaintenancee(status, fromDate, toDate);
    }

}


