package com.example.skyfast_2_0.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "classcategory")
public class Classcategory {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "seat_each_row", nullable = false)
    private Integer seatEachRow;

    @Size(max = 255)
    @NotNull
    @Column(name = "image", nullable = false)
    private String image;

    @NotNull
    @Column(name = "surcharge", nullable = false)
    private Float surcharge;
}
