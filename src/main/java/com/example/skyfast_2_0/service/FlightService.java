
package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.Classcategory;
import com.example.skyfast_2_0.entity.Flight;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.repository.ClassCategoryRepository;
import com.example.skyfast_2_0.repository.FlightRepository;
import com.example.skyfast_2_0.repository.RouteRepository;
import com.example.skyfast_2_0.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    public Flight findFlightById(Integer id){
        return flightRepository.findById(id).orElse(null);
    }

    public List<Flight> findTopCheapestFlightsByRoute() {
        return getFlightsbyStatus(flightRepository.findTopCheapestFlightsByRoute());
    }

    public List<Flight> getFlightsbyDepaTime(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        return getFlightsbyStatus(flightRepository.findFlightsByDepartureTimeRange(startOfDay, endOfDay));
    }

    public List<Flight> getFlightsbyStatus(List<Flight> searchFlight) {
        List<Flight> flights = new ArrayList<>();
        for (Flight flight : searchFlight) {
            if (flight.getStatusFlight().equalsIgnoreCase("Active")) {
                flights.add(flight);
            }
        }
        return flights;
    }

    public List<Flight> searchFlights(String departureAirport, String arrivalAirport, LocalDate departureDateStart, Integer classCategoryId, String passengerCount) {
        Integer departureAirportId = routeRepository.airportIdByDepartureAirport(departureAirport);
        Integer arrivalAirportId = routeRepository.airportIdByDepartureAirport(arrivalAirport);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(departureDateStart.atStartOfDay().format(formatter), formatter);
        LocalDateTime endDate = LocalDateTime.parse(departureDateStart.atTime(23, 59, 59).format(formatter), formatter);

        Integer totalPassengers = getTotalPassengers(passengerCount);
        String classCategoryName = getClassName(classCategoryId);
        List<Flight> searchFlights = checkPassengerCount(flightRepository.searchFlights(departureAirportId, arrivalAirportId, startDate, endDate, classCategoryName), totalPassengers, classCategoryName);
        System.out.println(departureAirportId + " " + arrivalAirportId + " " + totalPassengers + " "+ startDate.toString() + " " + endDate.toString() + " " + classCategoryName);
//        return flightRepository.searchFlights(departureAirportId, arrivalAirportId, startDate, endDate, classCategoryName);

        return getFlightsbyStatus(searchFlights);
    }
    public int getTotalPassengers(String passengerCount) {
        int total = 0;
        String[] parts = passengerCount.split(","); // Tách từng phần (Người lớn, Trẻ em, Em bé)

        for (String part : parts) {
            String number = part.trim().split(" ")[0]; // Lấy số đầu tiên trong mỗi phần
            total += Integer.parseInt(number);
        }
        return total;
    }

    public int getTotalSeat(Integer airplaneId, String classChoice){
        List<Classcategory> classcategoryList = classCategoryRepository.findByAirplaneId(airplaneId);
        int count = 0;
        for (Classcategory classcategory : classcategoryList) {
            if (classcategory.getName().contains(classChoice)) {
                count = classcategory.getTotalSeats();
            }
        }
        return count;
    }

    public int getTotalSeatChoice(Integer flightId,String classChoice){
        List<Ticket> ticketList = ticketRepository.findByFlightId(flightId);
        int count = 0;
        for (Ticket ticket : ticketList) {
            if (ticket.getClassCategory().getName().contains(classChoice)) {
                count++;
            }
        }
        return count;
    }

    public Integer seatLeft(Flight flight, Integer classChoice){
        String classChoiceName = getClassName(classChoice);
        return getTotalSeat(flight.getAirplane().getId(), classChoiceName) - getTotalSeatChoice(flight.getId(), classChoiceName);
    }

    public List<Flight> checkPassengerCount(List<Flight> searchFlights, int passengerCount, String classChoice) {
        return searchFlights.stream()
                .filter(flight -> passengerCount <= (getTotalSeat(flight.getAirplane().getId(), classChoice) - getTotalSeatChoice(flight.getId(), classChoice)))
                .collect(Collectors.toList());
    }
    public String getClassName(Integer classChoice){
        List<Classcategory> classcategoryList = classCategoryRepository.findAll();
        String className = "";
        for (Classcategory classcategory : classcategoryList) {
            if (classcategory.getId().equals(classChoice)) {
                className = classcategory.getName();
            }
        }
        return className;
    }

    public List<Integer> getPassengers(String text){

        String[] parts = text.split(", ");
        List<Integer> peopleCounts = new ArrayList<>();

        for (String part : parts) {
            peopleCounts.add(Integer.parseInt(part.split(" ")[0]));
        }
        return peopleCounts;
    }



    public List<String> priceEachPassenger(String passengerCount, Integer classChoice, Flight flight){
        float classMultiplier=0f;
        String className = getClassName(classChoice);
        // Premium Economy
        classMultiplier = switch (className) {
            case "Economy" -> 1.0f;  // Economy
            case "Business" -> 1.5f;
            default -> classMultiplier;
        };
        List<Integer> getPassengers = getPassengers(passengerCount);
        // 3. Tính tổng giá
        float basePrice = flight.getPrice();

        List<String> priceList = new ArrayList<>();
        if (getPassengers.get(0) > 0) priceList.add(getPassengers.get(0) * 1.0f * basePrice * classMultiplier + "");
        if (getPassengers.get(1) > 0) priceList.add(getPassengers.get(1) * 0.9f * basePrice * classMultiplier + "");
        if (getPassengers.get(2) > 0) priceList.add(getPassengers.get(2) * 0.8f * basePrice * classMultiplier + "");

        return priceList;

    }

    public String totalPrice(String text, Integer classChoice, Flight flight) {
        float classMultiplier=0f;
        String className = getClassName(classChoice);
        // Premium Economy
        classMultiplier = switch (className) {
            case "Economy" -> 1.0f;  // Economy
            case "Business" -> 1.5f;
            default -> classMultiplier;
        };
        List<Integer> getPassengers = getPassengers(text);
        // 3. Tính tổng giá
        float basePrice = flight.getPrice();
        float totalPrice = ((getPassengers.get(0) * 1.0f + getPassengers.get(1) * 0.9f + getPassengers.get(2) * 0.8f) * basePrice) * classMultiplier;

        return totalPrice + "";

    }
}
