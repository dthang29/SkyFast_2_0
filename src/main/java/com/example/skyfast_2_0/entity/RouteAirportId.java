package com.example.skyfast_2_0.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class RouteAirportId implements java.io.Serializable {
    private static final long serialVersionUID = 3319630297142337971L;
    @NotNull
    @Column(name = "airport_id", nullable = false)
    private Integer airportId;

    @NotNull
    @Column(name = "route_id", nullable = false)
    private Integer routeId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RouteAirportId entity = (RouteAirportId) o;
        return Objects.equals(this.routeId, entity.routeId) &&
                Objects.equals(this.airportId, entity.airportId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(routeId, airportId);
    }

}