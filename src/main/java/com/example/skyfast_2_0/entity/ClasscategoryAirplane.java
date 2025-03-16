package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classcategory_airplane")
public class ClasscategoryAirplane {
    @EmbeddedId
    private ClasscategoryAirplaneId id;

    @MapsId("classCategoryId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "class_category_id", nullable = false)
    private Classcategory classCategory;

    @MapsId("airplaneId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "airplane_id", nullable = false)
    private Airplane airplane;

}