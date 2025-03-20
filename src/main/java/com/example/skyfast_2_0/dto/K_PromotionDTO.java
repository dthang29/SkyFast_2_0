package com.example.skyfast_2_0.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Setter
@Getter
public class K_PromotionDTO {
    private Integer id;
    private String code;
    private String description;
    private Integer discountPercentage;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private Integer airlineId;
    private String airlineName;

   public K_PromotionDTO(Integer id, String code, String description, Integer discountPercentage,
                         LocalDate startDate, LocalDate endDate, String status,
                         Integer airlineId, String airlineName) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.airlineId = airlineId;
        this.airlineName = airlineName;
    }

    public K_PromotionDTO() {
    }
}
