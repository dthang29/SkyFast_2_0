
package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airline;
import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.entity.User;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/flight")
public class FlightController {

    @Autowired
    private AirlineService airline;
    @Autowired
    private FlightService flightService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private ClassCategoryService classCategoryService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private T_UserRepository t_userRepository;

    @GetMapping
    public String searchFlights(@RequestParam(value = "departure", required = false) String from,
                                @RequestParam(value = "arrival", required = false) String to,
                                @RequestParam(value = "departuredate", required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
                                @RequestParam(value = "people", required = false) String passengerCount,
                                @RequestParam(value = "class", required = false) Integer flightClass,
                                @RequestParam(value = "airlinecheck", required = false) List<Integer> selectedAirlines,
                                @RequestParam(value = "departuretime", required = false) List<String> selectedDeTimes,
                                @RequestParam(value = "arrivaltime", required = false) List<String> selectedArTimes,
                                @RequestParam(value = "departureHome", required = false) String departureHome,
                                @RequestParam(value = "arrivalHome", required = false) String arrivalHome,
                                @RequestParam(value = "departureTimeHome", required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureTimeHome,

                                Model model, Authentication authentication) {
        // Gọi initModelData để chuẩn bị dữ liệu cho giao diện (VD: danh sách sân bay, hãng bay...)
        initModelData(model);
        if(authentication != null){
            String email = userProfileService.getUserEmail(authentication);
            User user = t_userRepository.findByEmail(email);
            model.addAttribute("username", user.getUserName());
        }
        System.out.println("??????");
        System.out.println("check: " + from + to + departureDate + passengerCount + flightClass + passengerCount);
        // Kiểm tra nếu thiếu tham số thì chỉ hiển thị trang, không thực hiện tìm kiếm

        if (departureHome != null && arrivalHome != null && departureTimeHome != null) {
            from = departureHome;
            to = arrivalHome;
            departureDate = departureTimeHome;
            passengerCount = "1 Người lớn, 0 Trẻ em, 0 Em bé";
            flightClass = 1;
        }
        else if (from == null || to == null || departureDate == null || passengerCount == null || flightClass == null) {
            return "Flight"; // Không gọi searchFlights, chỉ hiển thị giao diện
        }
        System.out.println(from+ to+ departureDate+ flightClass+ passengerCount);

        // Khi có đầy đủ tham số thì mới thực hiện tìm kiếm
        List<Flight> searchFlights = flightService.searchFlights(from, to, departureDate, flightClass, passengerCount);
        System.out.println("Số lượng chuyến bay tìm thấy: " + searchFlights.size());

        for (Flight flight : searchFlights) {
            System.out.println(flight); // Kiểm tra thông tin từng chuyến bay
        }
        // Lọc theo airline nếu có chọn
        if (selectedAirlines != null && !selectedAirlines.isEmpty()) {
            searchFlights.removeIf(flight -> !selectedAirlines.contains(flight.getAirline().getId()));
        }
        if (selectedDeTimes != null && !selectedDeTimes.isEmpty()) {
            searchFlights.removeIf(flight ->
                    !isFlightInSelectedTime(flight.getDepartureTime().getHour(), selectedDeTimes)
            );
        }
        if (selectedArTimes != null && !selectedArTimes.isEmpty()) {
            searchFlights.removeIf(flight ->
                    !isFlightInSelectedTime(flight.getArrivalTime().getHour(), selectedArTimes)
            );
        }



        model.addAttribute("searchFlights", searchFlights);
        model.addAttribute("departure", from);
        model.addAttribute("arrival", to);
        model.addAttribute("departuredate", departureDate);
        model.addAttribute("passengerCount", passengerCount);
        model.addAttribute("flightClass", flightClass);
        model.addAttribute("selectedAirlines", selectedAirlines);
        model.addAttribute("selectedDeTimes", selectedDeTimes);
        model.addAttribute("selectedArTimes", selectedArTimes);
        return "Flight";
    }

    @PostMapping
    public String getAirline(Model model) {
        initModelData(model);
        return "Flight";
    }

    // Tách riêng logic lấy dữ liệu chung
    private void initModelData(Model model) {
        List<Airline> airlines = airline.getAllAirlines();
        model.addAttribute("airlines", airlines);

        List<Object[]> allRoutes = routeService.getAllRoutesWithAirportNames();

        // Lấy danh sách sân bay đi
        Set<String> uniqueRoutesDeparture = allRoutes.stream()
                .map(route -> route[1] != null ? route[1].toString() : "") // Kiểm tra null trước khi convert
                .filter(name -> !name.isEmpty()) // Bỏ giá trị rỗng
                .collect(Collectors.toSet());
        model.addAttribute("uniqueRoutesDeparture", uniqueRoutesDeparture);

        // Lấy danh sách sân bay đến
        Set<String> uniqueRoutesArrival = allRoutes.stream()
                .map(route -> route[2] != null ? route[2].toString() : "")
                .filter(name -> !name.isEmpty())
                .collect(Collectors.toSet());
        model.addAttribute("uniqueRoutesArrival", uniqueRoutesArrival);

        // Lấy danh sách hạng ghế
        List<Classcategory> classcategoryList = classCategoryService.getAllClasCategories();
        model.addAttribute("classcategories", classcategoryList);

        List<Object[]> routes = routeService.getAllRoutesWithAirportNames();
        Map<Integer, String[]> routeMap = new HashMap<>();
        for (Object[] route : routes) {
            routeMap.put((Integer) route[0], new String[]{(String) route[1], (String) route[2]});
        }
        model.addAttribute("routeMap", routeMap);
    }
    private boolean isFlightInSelectedTime(int flightHour, List<String> selectedTimes) {
        for (String timeRange : selectedTimes) {
            String[] range = timeRange.split("-");
            int start = Integer.parseInt(range[0]);
            int end = Integer.parseInt(range[1]);

            if (start < end) {
                if (flightHour >= start && flightHour < end) {
                    return true;
                }
            } else {
                if (flightHour >= start || flightHour < end) { // Khoảng 18:00 - 00:00
                    return true;
                }
            }
        }
        return false;
    }

}