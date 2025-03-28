package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Baggage;
import com.example.skyfast_2_0.entity.Ticket;
import com.example.skyfast_2_0.entity.TicketBaggage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketBaggageRepository extends JpaRepository<TicketBaggage, Integer> {
    @Modifying
    @Query("insert into TicketBaggage (ticket, baggage) values (:ticket, :baggage)")
    void insertTicketBaggage(@Param("ticket") Ticket ticket,
                                      @Param("baggage") Baggage baggage);
}
