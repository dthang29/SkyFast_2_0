package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.repository.FlightRepository;
import com.example.skyfast_2_0.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private RouteRepository routeRepository;

    public List<Flight> getFlightsbyDepaTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return flightRepository.findFlightsByDepartureTimeRange(startOfDay, endOfDay);
    }

    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, LocalDate departureDateStart, Integer classCategoryId, String passengerCount) {
        Integer departureAirportId = routeRepository.airportIdByDepartureAirport(departureAirport);
        Integer arrivalAirportId = routeRepository.airportIdByDepartureAirport(arrivalAirport);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(departureDateStart.atStartOfDay().format(formatter), formatter);
        LocalDateTime endDate = LocalDateTime.parse(departureDateStart.atTime(23, 59, 59).format(formatter), formatter);
        Integer totalPassengers = getTotalPassengers(passengerCount);

        System.out.println(departureAirportId + " " + arrivalAirportId + " " + totalPassengers + " "+ startDate.toString() + " " + endDate.toString() + " " + classCategoryId);
        return flightRepository.searchFlights(departureAirportId, arrivalAirportId, startDate, endDate, classCategoryId, totalPassengers);

    }
    public static int getTotalPassengers(String passengerCount) {
        int total = 0;
        String[] parts = passengerCount.split(","); // Tách từng phần (Người lớn, Trẻ em, Em bé)

        for (String part : parts) {
            String number = part.trim().split(" ")[0]; // Lấy số đầu tiên trong mỗi phần
            total += Integer.parseInt(number);
        }
        return total;
    }


}