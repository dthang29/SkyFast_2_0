package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query("SELECT t FROM Ticket t " +
            "JOIN t.flight f " +
            "WHERE t.id = (SELECT t2.id FROM Ticket t2 WHERE t2.flight.id = f.id ORDER BY t2.ticketPrice ASC, t2.id ASC LIMIT 1) " +
            "ORDER BY t.ticketPrice ASC")

    List<Ticket> findTop10CheapestTickets();
}
