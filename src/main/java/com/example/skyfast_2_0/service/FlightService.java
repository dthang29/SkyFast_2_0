
package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.repository.ClassCategoryRepository;
import com.example.skyfast_2_0.repository.FlightRepository;
import com.example.skyfast_2_0.repository.RouteRepository;
import com.example.skyfast_2_0.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FlightService {
    @Autowired
    private FlightRepository flightRepository;
    @Autowired
    private RouteRepository routeRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ClassCategoryRepository classCategoryRepository;

    public List<Flight> findTopCheapestFlightsByRoute() {
        return flightRepository.findTopCheapestFlightsByRoute();
    }

    public List<Flight> getFlightsbyDepaTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return flightRepository.findFlightsByDepartureTimeRange(startOfDay, endOfDay);
    }

    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, LocalDate departureDateStart, Integer classCategoryId, String passengerCount) {
        Integer departureAirportId = routeRepository.airportIdByDepartureAirport(departureAirport);
        Integer arrivalAirportId = routeRepository.airportIdByDepartureAirport(arrivalAirport);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(departureDateStart.atStartOfDay().format(formatter), formatter);
        LocalDateTime endDate = LocalDateTime.parse(departureDateStart.atTime(23, 59, 59).format(formatter), formatter);

        Integer totalPassengers = getTotalPassengers(passengerCount);
        String classCategoryName = getClassName(classCategoryId);
        List<Flight> searchFlights = checkPassengerCount(flightRepository.searchFlights(departureAirportId, arrivalAirportId, startDate, endDate, classCategoryName), totalPassengers, classCategoryId);
        System.out.println(departureAirportId + " " + arrivalAirportId + " " + totalPassengers + " "+ startDate.toString() + " " + endDate.toString() + " " + classCategoryName);
//        return flightRepository.searchFlights(departureAirportId, arrivalAirportId, startDate, endDate, classCategoryName);
        return searchFlights;
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

    public int getTotalSeat(Integer airplaneId, Integer classChoice){
        List<Classcategory> classcategoryList = classCategoryRepository.findByAirplaneId(airplaneId);
        int count = 0;
        for (Classcategory classcategory : classcategoryList) {
            if (classcategory.getId().equals(classChoice)) {
                count = classcategory.getTotalSeats();
            }
        }
        return count;
    }

    public int getTotalSeatChoice(Integer flightId,Integer classChoice){
        List<Ticket> ticketList = ticketRepository.findByFlightId(flightId);
        int count = 0;
        for (Ticket ticket : ticketList) {
            if (ticket.getClassCategory().getId().equals(classChoice)) {
                count++;
            }
        }
        return count;
    }

    public List<Flight> checkPassengerCount(List<Flight> searchFlights, int passengerCount, Integer classChoice) {
        return searchFlights.stream()
                .filter(flight -> passengerCount <= (getTotalSeat(flight.getAirplane().getId(), classChoice) - getTotalSeatChoice(flight.getId(), classChoice)))
                .collect(Collectors.toList());
    }
    public String getClassName(Integer classChoice){
        List<Classcategory> classcategoryList = classCategoryRepository.findAllClassCategories();
        String className = "";
        for (Classcategory classcategory : classcategoryList) {
            if (classcategory.getId().equals(classChoice)) {
                className = classcategory.getName();
            }
        }
        return className;
    }
}
