package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.repository.D_PlaneCategoryRepository;
import com.example.skyfast_2_0.service.D_SeatCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/admin/seatCategory")
public class D_SeatCategoryController {

    private static final String UPLOAD_DIR = "src/main/resources/static/img/";

    @Autowired
    private D_SeatCategoryService DSeatCategoryService;

    @Autowired
    private D_PlaneCategoryRepository DAirplaneRepository; // Giả sử repository này đã có sẵn

    // Hiển thị danh sách hạng ghế của máy bay
    @GetMapping("/{airplaneId}")
    public String viewSeatCategories(@PathVariable Integer airplaneId, Model model) {
        Airplane airplane = DAirplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new RuntimeException("Airplane not found"));
        List<Classcategory> categories = DSeatCategoryService.findByAirplane(airplane);
        model.addAttribute("airplane", airplane);
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle", "Seat Category Management");
        return "seatCategoryList"; // tên file template
    }

    @GetMapping("/edit/{id}")
    @ResponseBody
    public ResponseEntity<Classcategory> getSeatCategory(@PathVariable("id") Integer id) {
        // Tìm hạng ghế theo ID
        Classcategory classcategory = DSeatCategoryService.findById(id);

        // Nếu không tìm thấy hạng ghế, trả về lỗi
        if (classcategory == null) {
            return ResponseEntity.status(404).body(null);  // 404 nếu không tìm thấy
        }

        // Trả về đối tượng Classcategory dưới dạng JSON
        return ResponseEntity.ok(classcategory);  // Trả về thông tin hạng ghế
    }

    @PostMapping("/add")
    public String addSeatCategory(
            @RequestParam("name") String name,
            @RequestParam("totalSeats") Integer totalSeats,
            @RequestParam("surcharge") Float surcharge,
            @RequestParam("description") String description,
            @RequestParam("airplaneId") Integer airplaneId,
            @RequestParam("imageFile") MultipartFile imageFile, // Nhận ảnh
            RedirectAttributes redirectAttributes) {

        // Lấy máy bay dựa trên ID
        Airplane airplane = DAirplaneRepository.findById(airplaneId)
                .orElseThrow(() -> new RuntimeException("Airplane not found"));

        // Kiểm tra số lượng hạng ghế đã có
        List<Classcategory> existingCategories = DSeatCategoryService.findByAirplane(airplane);
        if (existingCategories.size() >= 2) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã có đủ 2 hạng ghế cho máy bay này.");
            return "redirect:/admin/seatCategory/" + airplaneId;
        }

        // Kiểm tra nếu hạng ghế đã tồn tại
        boolean isExist = DSeatCategoryService.existsByAirplaneAndName(airplane, name);
        if (isExist) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hạng ghế này đã tồn tại.");
            return "redirect:/admin/seatCategory/" + airplaneId;
        }

        // Kiểm tra nếu tên hạng ghế hợp lệ (chỉ có 2 hạng ghế là Economy và Business)
        if (!("Economy".equals(name) || "Business".equals(name))) {
            redirectAttributes.addFlashAttribute("errorMessage", "Hạng ghế không hợp lệ. Chỉ có 2 hạng 'Economy' và 'Business'.");
            return "redirect:/admin/seatCategory/" + airplaneId;
        }
        int sumSeats = existingCategories.stream().mapToInt(Classcategory::getTotalSeats).sum() + totalSeats;
        if (sumSeats > airplane.getSeatCapacity()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tổng số ghế các hạng không thể vượt quá số ghế của máy bay (" + airplane.getSeatCapacity() + ").");
            return "redirect:/admin/seatCategory/" + airplaneId;
        }

        try {
            // Lưu file hình ảnh vào thư mục
            String fileName = imageFile.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            imageFile.transferTo(filePath);

            // Tạo đối tượng Classcategory và lưu thông tin
            Classcategory classcategory = new Classcategory();
            classcategory.setName(name);
            classcategory.setTotalSeats(totalSeats);
            classcategory.setSurcharge(surcharge);
            classcategory.setDescription(description);
            classcategory.setImage("/img/" + fileName); // Lưu đường dẫn ảnh
            classcategory.setAirplane(airplane); // Gắn hạng ghế với máy bay

            // Lưu vào database
            DSeatCategoryService.save(classcategory);

            // Cập nhật thông báo thành công
            redirectAttributes.addFlashAttribute("successMessage", "Thêm hạng ghế thành công!");
        } catch (IOException e) {
            // Xử lý lỗi nếu có
            redirectAttributes.addFlashAttribute("errorMessage", "Thêm hạng ghế thất bại!");
            e.printStackTrace();
        }

        return "redirect:/admin/seatCategory/" + airplaneId;
    }

    // Xử lý cập nhật hạng ghế (không update tên)
    @PostMapping("/update")
    public String updateSeatCategory(
            @RequestParam("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("totalSeats") Integer totalSeats,
            @RequestParam("surcharge") Float surcharge,
            @RequestParam("description") String description,
            @RequestParam(value = "image", required = false) MultipartFile imageFile,  // New image is optional
            RedirectAttributes redirectAttributes) {

        // Find the existing seat category by id
        Classcategory existingCategory = DSeatCategoryService.findById(id);
        if (existingCategory == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Seat Category not found.");
            return "redirect:/admin/seatCategory/";
        }
        List<Classcategory> existingCategories = DSeatCategoryService.findByAirplane(existingCategory.getAirplane());
        // Update the existing category with new data
        existingCategory.setName(name);  // Don't allow updating the name from the modal, if required
        existingCategory.setTotalSeats(totalSeats);
//        int sumSeats = existingCategories.stream().mapToInt(Classcategory::getTotalSeats).sum() + totalSeats;
        int sumSeats = existingCategories.stream()
                .filter(category -> !category.getId().equals(id))  // Exclude the current category being updated
                .mapToInt(Classcategory::getTotalSeats)
                .sum() + totalSeats;  // Add the new total seats of the updated category
        if (sumSeats > existingCategory.getAirplane().getSeatCapacity()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Tổng số ghế các hạng không thể vượt quá số ghế của máy bay (" + existingCategory.getAirplane().getSeatCapacity() + ").");
            return "redirect:/admin/seatCategory/" + existingCategory.getAirplane().getId();
        }
        existingCategory.setSurcharge(surcharge);
        existingCategory.setDescription(description);

        if (!imageFile.isEmpty()) {
            try {
                // Save new image if provided
                String fileName = imageFile.getOriginalFilename();
                Path filePath = Paths.get(UPLOAD_DIR + fileName);
                imageFile.transferTo(filePath);
                existingCategory.setImage("/img/" + fileName);
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("errorMessage", "Failed to upload the image.");
                return "redirect:/admin/seatCategory/" + existingCategory.getAirplane().getId();
            }
        }

        // Save the updated category
        DSeatCategoryService.save(existingCategory);
        redirectAttributes.addFlashAttribute("successMessage", "Seat Category updated successfully.");
        return "redirect:/admin/seatCategory/" + existingCategory.getAirplane().getId();
    }


}
