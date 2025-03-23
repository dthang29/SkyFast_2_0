package com.example.skyfast_2_0.controller;


import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.entity.Route;
import com.example.skyfast_2_0.service.AirportService;
import com.example.skyfast_2_0.service.K_RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/manager/routes")
public class RouteController {
    private final K_RouteService KRouteService;
    private final AirportService airportService;

   public RouteController(K_RouteService KRouteService, AirportService airportService) {
        this.KRouteService = KRouteService;
        this.airportService = airportService;
    }

    @GetMapping
    public String getAllRoutes(Model model) {
        List<Object[]> routes = KRouteService.findAllRoutesWithAirportNames();
        model.addAttribute("routes", routes);
        return "routeManagement";
    }

    @GetMapping("/add")
    public String showAddRouteForm() {
        return "routeCreate"; // Hiển thị form thêm tuyến đường
    }

    // Xử lý thêm tuyến đường
    @PostMapping("/add")
    public String addRoute(@RequestParam String departureAirportName,
                           @RequestParam String arrivalAirportName,
                           @RequestParam double distance,
                           Model model) {
        // Tìm sân bay dựa trên tên
        Airport departureAirport = airportService.findByName(departureAirportName);
        Airport arrivalAirport = airportService.findByName(arrivalAirportName);

        // Kiểm tra nếu không tìm thấy sân bay
        if (departureAirport == null || arrivalAirport == null) {
            model.addAttribute("errorMessage", "Sân bay không tồn tại!");
            return "routeCreate";
        }

        // Kiểm tra tuyến đường có bị trùng không
        if (KRouteService.isRouteExists(departureAirport.getId(), arrivalAirport.getId())) {
            model.addAttribute("errorMessage", "Tuyến đường này đã tồn tại!");
            return "routeCreate";
        }

        // Lưu tuyến đường mới
        Route newRoute = new Route();
        newRoute.setDepartureAirportId(departureAirport.getId());
        newRoute.setArrivalAirportId(arrivalAirport.getId());
        newRoute.setDistance((int) distance);
        KRouteService.saveRoute(newRoute);

        return "redirect:/manager/routes";
    }

}
