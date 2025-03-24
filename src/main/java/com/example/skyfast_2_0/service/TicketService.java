package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.entity.*;
import com.example.skyfast_2_0.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    TicketRepository ticketRepository;
    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    ClassCategoryRepository classCategoryRepository;
    @Transactional
    public void insertTicket(Integer flight, Integer classCategory) {
        List<Ticket> tickets = ticketRepository.findByFlightId(flight);
        Ticket ticket = tickets.getLast();
        Flight flightById = flightRepository.findById(flight).orElseThrow();
        String seatCode = getNextSeat(ticket.getSeatCode(), 6, flightById.getAirplane().getSeatCapacity());
        ticketRepository.insertTicket(seatCode, "Processing",
                bookingRepository.findBookingWithMaxId(),
                flightRepository.findById(flight).orElseThrow(),
                passengerRepository.finePassengerByMaxId(),
                classCategoryRepository.findById(classCategory).orElseThrow());
    }

    public String getNextSeat(String currentSeat, int seatsPerRow, int maxSeats) {
        // Nếu currentSeat rỗng hoặc null, trả về ghế đầu tiên "A1"
        if (currentSeat == null || currentSeat.isEmpty()) {
            return maxSeats > 0 ? "A1" : null; // Trả về null nếu không có ghế nào
        }

        char row = currentSeat.charAt(0); // Lấy ký tự hàng (A, B, C...)
        int seatNumber = Integer.parseInt(currentSeat.substring(1)); // Lấy số ghế

        // Tính số thứ tự của ghế hiện tại (vd: A1 = 1, A6 = 6, B1 = 7,...)
        int currentSeatIndex = (row - 'A') * seatsPerRow + seatNumber;

        // Nếu ghế tiếp theo vượt quá maxSeats, trả về null
        if (currentSeatIndex >= maxSeats) {
            return null;
        }

        // Nếu chưa đến ghế cuối cùng của hàng, tăng số ghế
        if (seatNumber < seatsPerRow) {
            return row + String.valueOf(seatNumber + 1);
        } else {
            // Nếu đã đến ghế cuối cùng của hàng, chuyển sang hàng tiếp theo
            char nextRow = (char) (row + 1); // Chuyển sang hàng tiếp theo
            return nextRow + "1";
        }
    }
}
