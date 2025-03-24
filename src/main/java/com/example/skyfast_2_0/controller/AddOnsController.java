
package com.example.skyfast_2_0.controller;


import com.example.skyfast_2_0.entity.*;
import com.example.skyfast_2_0.repository.T_UserRepository;
import com.example.skyfast_2_0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/addons")
public class AddOnsController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private V_AirportService VAirportService;
    @Autowired
    private ClassCategoryService classCategoryService;
    @Autowired
    private BaggageService baggageService;
    @Autowired
    private PassengerService passengerService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private T_UserRepository userRepository;
    @GetMapping
    public String getAddOns(@RequestParam("flightId") Integer flightId,
                            @RequestParam("passengerCount") String passengerCount,
                            @RequestParam("flightClass") Integer flightClass,
                            @RequestParam(value = "baggageQuantity", required = false, defaultValue = "0") Integer baggageQuantity,
                            @RequestParam(value = "baggage", required = false, defaultValue = "") String baggage,
                            @RequestParam(value = "title", required = false, defaultValue = "") String title,
                            @RequestParam(value = "LastName", required = false, defaultValue = "") String LastName,
                            @RequestParam(value = "FirstName", required = false, defaultValue = "") String FirstName,
                            @RequestParam(value = "nationality", required = false, defaultValue = "") String nationality,
                            @RequestParam(value = "email", required = false, defaultValue = "") String email,
                            @RequestParam(value = "phoneNumber", required = false, defaultValue = "") String phoneNumber,
                            @RequestParam(value = "method", required = false, defaultValue = "") String method,
                            Authentication authentication,
                            Model model){


        List<Object[]> routes = routeService.getAllRoutesWithAirportNames();
        Map<Integer, String[]> routeMap = new HashMap<>();
        for (Object[] route : routes) {
            routeMap.put((Integer) route[0], new String[]{(String) route[1], (String) route[2]});
        }
        model.addAttribute("routeMap", routeMap);

        Flight flight = flightService.findFlightById(flightId);
        model.addAttribute("flight",flight);

        Airport departureAirport = VAirportService.findAirportById(flight.getRoute().getDepartureAirportId());
        Airport arrivalAirport = VAirportService.findAirportById(flight.getRoute().getArrivalAirportId());
        model.addAttribute("departureAirport",departureAirport);
        model.addAttribute("arrivalAirport",arrivalAirport);

        String totalPassengerPrice = flightService.totalPrice(passengerCount,flightClass , flight);
        model.addAttribute("totalPassengerPrice",totalPassengerPrice);

        List<String> priceEachPassenger = flightService.priceEachPassenger(passengerCount,flightClass , flight);
        model.addAttribute("priceEachPassenger",priceEachPassenger);

        List<Integer> getPassenger = flightService.getPassengers(passengerCount);
        model.addAttribute("getPassenger",getPassenger);

        Integer getTotal= flightService.getTotalPassengers(passengerCount);
        model.addAttribute("getTotal",getTotal);

        List<Classcategory> classcategoryList = classCategoryService.getAllClasCategories();
        model.addAttribute("classcategories", classcategoryList);

        List<Baggage> baggageList = baggageService.findByAirlineId(flight.getId());
        model.addAttribute("baggageList", baggageList);

        // Chuyển chuỗi baggage thành danh sách số nguyên
        List<Integer> selectedBaggage = Arrays.stream(baggage.split(","))
                .filter(s -> !s.isEmpty()) // Loại bỏ giá trị rỗng
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        // Đảm bảo danh sách luôn có đủ số lượng theo baggageQuantity
        while (selectedBaggage.size() < baggageQuantity) {
            selectedBaggage.add(0); // Thêm hành lý mặc định (0kg)
        }
        while (selectedBaggage.size() > baggageQuantity) {
            selectedBaggage.remove(selectedBaggage.size() - 1); // Xóa hành lý dư thừa
        }
        System.out.println(selectedBaggage);
        String totalBaggagePrice = baggageService.totalPriceBagaage(selectedBaggage);
        model.addAttribute("totalBaggagePrice",totalBaggagePrice);

        System.out.println("totalBaggagePrice"+ totalBaggagePrice);
        String totalPrice = String.valueOf(Float.parseFloat(totalPassengerPrice) + Float.parseFloat(totalBaggagePrice));
        model.addAttribute("totalPrice",totalPrice);

        String emailAuth = userProfileService.getUserEmail(authentication);
        User user = userRepository.findByEmail(emailAuth);

        if(method.equals("paylater")){
            passengerService.insertPassenger(title, FirstName, LastName, nationality, phoneNumber, email);
            bookingService.insertBooking(Float.parseFloat(totalPrice), "PROCESSING", user.getId(), 1);
            ticketService.insertTicket(flight.getId(), flightClass);
            String bookingCode = bookingService.findBookingWithMaxId().getBookingCode();
            return "redirect:/homepage";
        }else if(method.equals("paynow")){
            return "redirect:/flight";
        }

        model.addAttribute("selectedBaggage", selectedBaggage);
        model.addAttribute("selectedQuantity", baggageQuantity);
        model.addAttribute("flightClass", flightClass);
        model.addAttribute("passengerCount", passengerCount);
        model.addAttribute("title", title);
        model.addAttribute("lastName", LastName);
        model.addAttribute("firstName", FirstName);
        model.addAttribute("nationality", nationality);
        model.addAttribute("email", email);
        model.addAttribute("phoneNumber", phoneNumber);

        System.out.println(baggageQuantity);
        System.out.println("Baggage: " + baggage);
        System.out.println(selectedBaggage);
        return "AddOns";
    }
    @PostMapping
    public String postAddOns(Model model) {
        return "AddOns";
    }
}