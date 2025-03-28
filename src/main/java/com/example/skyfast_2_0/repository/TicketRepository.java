
package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByFlightId(Integer flightId);
    @Modifying
    @Query("insert into Ticket(seatCode, status, booking, flight, passenger, classCategory) values (:seatCode, :status, :booking, :flight, :passenger, :classCategory)")
    void insertTicket(@Param("seatCode") String seatCode,
                      @Param("status") String status,
                      @Param("booking") Booking booking,
                      @Param("flight") Flight flight,
                      @Param("passenger") Passenger passenger,
                      @Param("classCategory") Classcategory classCategory);
    @Query("select t from Ticket t where t.id = (select max(t.id) from Ticket t)")
    Ticket findTicketByMaxId();
}
