
package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.*;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/homepage")
public class HomePageController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private AirlineService airlineService;
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
                                Model model, Authentication authentication) {

        // Gọi initModelData để chuẩn bị dữ liệu cho giao diện (VD: danh sách sân bay, hãng bay...)
        initModelData(model);
        if(authentication != null){
        String email = userProfileService.getUserEmail(authentication);
        User user = t_userRepository.findByEmail(email);
        model.addAttribute("username", user.getUserName());
        }
        // Kiểm tra nếu thiếu tham số thì chỉ hiển thị trang, không thực hiện tìm kiếm
        if (from == null || to == null || departureDate == null || passengerCount == null || flightClass == null) {
            return "HomePage"; // Không gọi searchFlights, chỉ hiển thị giao diện
        }
//        System.out.println(from+ to+ departureDate+ flightClass+ passengerCount);
//
//        // Khi có đầy đủ tham số thì mới thực hiện tìm kiếm
//        List<Flight> searchFlights = flightService.searchFlights(from, to, departureDate, flightClass, passengerCount);
//        System.out.println("Số lượng chuyến bay tìm thấy: " + searchFlights.size());
//
//        for (Flight flight : searchFlights) {
//            System.out.println(flight); // Kiểm tra thông tin từng chuyến bay
//        }
//        model.addAttribute("searchFlights", searchFlights);
//        model.addAttribute("departure", from);
//        model.addAttribute("arrival", to);
//        model.addAttribute("departuredate", departureDate);
//        model.addAttribute("passengerCount", passengerCount);
//        model.addAttribute("flightClass", flightClass);
        String encodedFrom = URLEncoder.encode(from, StandardCharsets.UTF_8);
        String encodedTo = URLEncoder.encode(to, StandardCharsets.UTF_8);
        String encodedPassengerCount = URLEncoder.encode(passengerCount, StandardCharsets.UTF_8);

        // Redirect với URL đã mã hóa
        return "redirect:/flight?departure=" + encodedFrom
                + "&arrival=" + encodedTo
                + "&departuredate=" + departureDate
                + "&people=" + encodedPassengerCount
                + "&class=" + flightClass;
    }


    @PostMapping
    public String homePagePost(Model model) {
        initModelData(model);
        return  "HomePage";
    }
    public void initModelData(Model model) {
        List<Flight> tickets = flightService.findTopCheapestFlightsByRoute();
        model.addAttribute("tickets", tickets);

        LocalDate date = LocalDate.of(2025, 3, 1);
        List<Flight> flights = flightService.getFlightsbyDepaTime(date);
        model.addAttribute("flights", flights);

        List<Object[]> routes = routeService.getAllRoutesWithAirportNames();
        Map<Integer, String[]> routeMap = new HashMap<>();
        for (Object[] route : routes) {
            routeMap.put((Integer) route[0], new String[]{(String) route[1], (String) route[2]});
        }
        model.addAttribute("routeMap", routeMap);

        List<Object[]> topArrivals = routeService.getTopPopularArrivalAirports();
        model.addAttribute("topArrivals", topArrivals);

        List<Object[]> topRoutes = routeService.getTopPopularRoutes();
        model.addAttribute("topRoutes", topRoutes);

        List<Airline> airlines = airlineService.getAllAirlines();
        model.addAttribute("airlines", airlines);

        List<Object[]> allRoutes = routeService.getAllRoutesWithAirportNames();
        // Sử dụng Set để loại bỏ trùng lặp
        Set<String> uniqueRoutesDeparture = allRoutes.stream()
                .map(route -> route[1].toString()) // Lấy phần tử thứ 2 (tên địa điểm)
                .collect(Collectors.toSet());

        model.addAttribute("uniqueRoutesDeparture", uniqueRoutesDeparture);
        Set<String> uniqueRoutesArrival = allRoutes.stream()
                .map(route -> route[2].toString()) // Lấy phần tử thứ 2 (tên địa điểm)
                .collect(Collectors.toSet());
        model.addAttribute("uniqueRoutesArrival", uniqueRoutesArrival);
        List<Classcategory> classcategoryList = classCategoryService.getAllClasCategories();
        model.addAttribute("classcategories", classcategoryList);
    }

}

