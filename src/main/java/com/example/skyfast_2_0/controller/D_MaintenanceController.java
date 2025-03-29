package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.dto.MaintenanceRequestDTO;
import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.dto.D_MaintenanceDTO;
import com.example.skyfast_2_0.entity.Maintenance;
import com.example.skyfast_2_0.repository.D_PlaneCategoryRepository;
import com.example.skyfast_2_0.service.D_MaintenanceService;
import com.example.skyfast_2_0.service.D_PlaneCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/admin/maintenance")
public class D_MaintenanceController {

    @Autowired
    private D_MaintenanceService DMaintenanceService;

    @Autowired
    private D_PlaneCategoryService DPlaneCategoryService;

    @Autowired
    private D_PlaneCategoryRepository DAirplaneRepository;

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> addMaintenance(@RequestBody MaintenanceRequestDTO request) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Lấy thông tin Airplane theo id
            Airplane airplane = DPlaneCategoryService.findById(request.getAirplaneId());
            if (airplane == null) {
                response.put("success", false);
                response.put("message", "Không tìm thấy máy bay với id: " + request.getAirplaneId());
                return response;
            }

            // Tính toán completionDate nếu chưa được truyền từ front-end
            LocalDate completionDate = request.getCompletionDate();
            if (completionDate == null && request.getMaintenanceDate() != null && request.getDuration() != null) {
                completionDate = request.getMaintenanceDate().plusDays(request.getDuration());
            }

            // Tạo đối tượng Maintenance và gán các thuộc tính
            Maintenance maintenance = new Maintenance();
            maintenance.setMaintenanceDate(request.getMaintenanceDate());
            maintenance.setDuration(request.getDuration());
            maintenance.setCompletionDate(completionDate);
            maintenance.setDescription(request.getDescription());
            maintenance.setMaintenanceStatus(request.getMaintenanceStatus());
            maintenance.setAirplane(airplane);

            // Lưu vào cơ sở dữ liệu
            DMaintenanceService.addMaintenance(maintenance);

            response.put("success", true);
//        response.put("redirectUrl", "/admin/maintenance/list");
            response.put("redirectUrl", "/admin/maintenance/list/" + request.getAirplaneId());
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Có lỗi xảy ra khi thêm bảo trì.");
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/list/{airplaneId}")
    public String getMaintenanceList(@PathVariable Integer airplaneId, Model model) {
        List<Maintenance> maintenanceList = DMaintenanceService.getMaintenanceByAirplaneId(airplaneId);
        model.addAttribute("maintenanceList", maintenanceList);
        model.addAttribute("airplaneId", airplaneId);
        model.addAttribute("airplane", DAirplaneRepository.findById(airplaneId).orElse(null));

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
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(required = false) String airplaneName // Thêm tham số này
    ) {
        // Gọi service để search
        return DMaintenanceService.searchMaintenancee(status, fromDate, toDate, airplaneName);
    }

}


