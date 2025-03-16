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
public class ClasscategoryAirplaneId implements java.io.Serializable {
    private static final long serialVersionUID = 8302041319839137766L;
    @NotNull
    @Column(name = "class_category_id", nullable = false)
    private Integer classCategoryId;

    @NotNull
    @Column(name = "airplane_id", nullable = false)
    private Integer airplaneId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ClasscategoryAirplaneId entity = (ClasscategoryAirplaneId) o;
        return Objects.equals(this.classCategoryId, entity.classCategoryId) &&
                Objects.equals(this.airplaneId, entity.airplaneId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classCategoryId, airplaneId);
    }

}