package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.service.D_AirlineService;
import com.example.skyfast_2_0.service.D_PlaneCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class D_PlaneCategoryController {
    private static final String UPLOAD_DIR = "src/main/resources/static/img/";
    @Autowired
    private D_PlaneCategoryService DPlaneCategoryService;
    @Autowired
    private D_AirlineService DAirlineService;

    @GetMapping("/admin/planeCategory")
    public String getPlaneCategories(Model model) {
        // Lấy danh sách các PlaneCategory từ dịch vụ
        List<Airplane> airplaneList = DPlaneCategoryService.getAllPlaneCategories();
        airplaneList.forEach(airplane -> System.err.println(airplane.getAirplaneName()));
        // Thêm dữ liệu vào model để Thymeleaf có thể truy xuất
        model.addAttribute("airplaneList", airplaneList);
        List<Airline> airlineList = DAirlineService.getAllAirlines();
        model.addAttribute("airlineList", airlineList);
        // Trả về view với tên 'planeCategory'
        return "planeCategory";
    }
    @PostMapping("/airplanes/add")
    public String addAirplane(
            @RequestParam("airplaneName") String airplaneName,
            @RequestParam("manufacturer") String manufacturer,
            @RequestParam("speed") Float speed,
            @RequestParam("totalLength") Float totalLength,
            @RequestParam("wingspan") Float wingspan,
            @RequestParam("height") Float height,
            @RequestParam("seatCapacity") Integer seatCapacity,
            @RequestParam("airplaneStatus") String airplaneStatus,
            @RequestParam("airlineId") Integer airlineId,
            @RequestParam("imageFile") MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        // Kiểm tra xem airplaneName đã tồn tại chưa
        boolean isExist = DPlaneCategoryService.existsByAirplaneName(airplaneName);
        if (isExist) {
            redirectAttributes.addFlashAttribute("errorMessage", "Airplane name already exists!");
            return "redirect:/admin/planeCategory";
        }

        try {
            // Lưu file vào thư mục
            String fileName = imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            imageFile.transferTo(filePath);

            Airline airline = DAirlineService.findById(airlineId);

            // Tạo đối tượng Airplane
            Airplane airplane = new Airplane();
            airplane.setAirplaneName(airplaneName);
            airplane.setManufacturer(manufacturer);
            airplane.setSpeed(speed);
            airplane.setTotalLength(totalLength);
            airplane.setWingspan(wingspan);
            airplane.setHeight(height);
            airplane.setSeatCapacity(seatCapacity);
            airplane.setAirplaneStatus(airplaneStatus);
            airplane.setAirplaneImage("/img/" + fileName);
            airplane.setAirline(airline);

            // Lưu vào database
            DPlaneCategoryService.saveAirplane(airplane);
            redirectAttributes.addFlashAttribute("successMessage", "Airplane created successfully!");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save airplane!");
            e.printStackTrace();
        }

        return "redirect:/admin/planeCategory";
    }


    @GetMapping("/admin/planeCategory/search")
    public String searchAirplanes(@RequestParam(value = "name", required = false) String name,
                                  @RequestParam(value = "status", required = false) String status,
                                  @RequestParam(value = "airlineName", required = false) String airlineName,
                                  Model model) {

        if (name != null && name.trim().isEmpty()) {
            name = null;
        }
        if (status != null && status.trim().isEmpty()) {
            status = null;
        }
        if (airlineName != null && airlineName.trim().isEmpty()) {
            airlineName = null;
        }

        // Search based on the airplane name, status, and airline name
        List<Airplane> airplaneList = DPlaneCategoryService.searchAirplanes(name, status, airlineName);
        model.addAttribute("airplaneList", airplaneList);
        return "planeCategory";
    }


    @GetMapping("/airplanes/edit")
    public String editAirplane(@RequestParam("id") Integer id, Model model) {
        Airplane airplane = DPlaneCategoryService.findById(id);
        List<Airline> airlineList = DAirlineService.getAllAirlines();

        if (airplane != null) {
            model.addAttribute("airplane", airplane);
        }
        model.addAttribute("airlineList", airlineList);

        return "planeCategory"; // Hiển thị lại form với dữ liệu được load
    }

    @PostMapping("/airplanes/update")
    public String updateAirplane(
            @RequestParam("id") Integer id,
            @RequestParam("airplaneName") String airplaneName,
            @RequestParam("manufacturer") String manufacturer,
            @RequestParam("speed") Float speed,
            @RequestParam("totalLength") Float totalLength,
            @RequestParam("wingspan") Float wingspan,
            @RequestParam("height") Float height,
            @RequestParam("seatCapacity") Integer seatCapacity,
            @RequestParam("airplaneStatus") String airplaneStatus,
            @RequestParam("airlineId") Integer airlineId,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
            RedirectAttributes redirectAttributes) {

        Airplane airplane = DPlaneCategoryService.findById(id);
        if (airplane != null) {
            airplane.setAirplaneName(airplaneName);
            airplane.setManufacturer(manufacturer);
            airplane.setSpeed(speed);
            airplane.setTotalLength(totalLength);
            airplane.setWingspan(wingspan);
            airplane.setHeight(height);
            airplane.setSeatCapacity(seatCapacity);
            airplane.setAirplaneStatus(airplaneStatus);

            Airline airline = DAirlineService.findById(airlineId);
            airplane.setAirline(airline);

            // Nếu có ảnh mới, cập nhật ảnh
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get("src/main/resources/static/img/" + fileName);
                try {
                    imageFile.transferTo(filePath);
                    airplane.setAirplaneImage("/img/" + fileName);
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("errorMessage", "Failed to update airplane image!");
                    return "redirect:/admin/planeCategory";
                }
            }

            DPlaneCategoryService.saveAirplane(airplane);
            redirectAttributes.addFlashAttribute("successMessage", "Airplane updated successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Airplane not found!");
        }

        return "redirect:/admin/planeCategory";
    }

    @GetMapping("/airplanes/get")
    @ResponseBody
    public Airplane getAirplane(@RequestParam("id") Integer id) {
        return DPlaneCategoryService.findById(id);
    }




}
