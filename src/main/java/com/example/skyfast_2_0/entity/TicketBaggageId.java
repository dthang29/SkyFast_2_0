package com.example.skyfast_2_0.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class TicketBaggageId implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = -1461823924069246789L;
    @NotNull
    @Column(name = "ticket_id", nullable = false)
    private Integer ticketId;

    @NotNull
    @Column(name = "baggage_id", nullable = false)
    private Integer baggageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TicketBaggageId entity = (TicketBaggageId) o;
        return Objects.equals(this.baggageId, entity.baggageId) &&
                Objects.equals(this.ticketId, entity.ticketId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baggageId, ticketId);
    }

}
