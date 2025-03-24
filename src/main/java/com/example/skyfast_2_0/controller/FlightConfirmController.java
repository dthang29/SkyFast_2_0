
package com.example.skyfast_2_0.controller;

import com.example.skyfast_2_0.entity.Airport;
import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.entity.Flight;

import com.example.skyfast_2_0.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/flightconfirm")
public class FlightConfirmController {
    @Autowired
    private FlightService flightService;
    @Autowired
    private RouteService routeService;
    @Autowired
    private AirportService airportService;
    @Autowired
    private ClassCategoryService classCategoryService;


    @GetMapping
    public String getFlightConfirm( @RequestParam("flightId") Integer flightId,
                                    @RequestParam("passengerCount") String passengerCount,
                                    @RequestParam("flightClass") Integer flightClass,
                                    Model model){

        List<Object[]> routes = routeService.getAllRoutesWithAirportNames();
        Map<Integer, String[]> routeMap = new HashMap<>();
        for (Object[] route : routes) {
            routeMap.put((Integer) route[0], new String[]{(String) route[1], (String) route[2]});
        }
        model.addAttribute("routeMap", routeMap);

        Flight flight = flightService.findFlightById(flightId);
        model.addAttribute("flight",flight);

        System.out.println(flightId);
        Airport departureAirport = airportService.findAirportById(flight.getRoute().getDepartureAirportId());
        Airport arrivalAirport = airportService.findAirportById(flight.getRoute().getArrivalAirportId());
        model.addAttribute("departureAirport",departureAirport);
        model.addAttribute("arrivalAirport",arrivalAirport);

        String totalPrice = flightService.totalPrice(passengerCount,flightClass , flight);
        model.addAttribute("totalPrice",totalPrice);

        List<String> priceEachPassenger = flightService.priceEachPassenger(passengerCount,flightClass , flight);
        model.addAttribute("priceEachPassenger",priceEachPassenger);

        List<Integer> getPassenger = flightService.getPassengers(passengerCount);
        model.addAttribute("getPassenger",getPassenger);

        Integer getTotal= flightService.getTotalPassengers(passengerCount);
        model.addAttribute("getTotal",getTotal);

        List<Classcategory> classcategoryList = classCategoryService.getAllClasCategories();
        model.addAttribute("classcategories", classcategoryList);



        model.addAttribute("flightClass", flightClass);
        model.addAttribute("passengerCount", passengerCount);



        return "FlightConfirm";
    }
    @PostMapping
    public String getAirline(Model model) {
        return "FlightConfirm";
    }
}