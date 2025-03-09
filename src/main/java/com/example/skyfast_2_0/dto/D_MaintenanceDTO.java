package com.example.skyfast_2_0.dto;

import java.time.LocalDate;

public class D_MaintenanceDTO {
    private String description;
    private Integer duration;
    private String maintenanceStatus;
    private Integer airplaneId;
    private LocalDate maintenanceDate; // Ngày bảo trì sẽ được set khi tạo
    private String airplaneName;

    // Constructor mặc định
    public D_MaintenanceDTO() {
        this.maintenanceDate = LocalDate.now(); // Mặc định lấy ngày hiện tại
        this.maintenanceStatus = "Unprocessed"; // Trạng thái mặc định
    }

    // Getter và Setter
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getAirplaneName() {
        return airplaneName;
    }

    public void setAirplaneName(String airplaneName) {
        this.airplaneName = airplaneName;
    }

    public String getMaintenanceStatus() {
        return maintenanceStatus;
    }

    public void setMaintenanceStatus(String maintenanceStatus) {
        this.maintenanceStatus = maintenanceStatus;
    }

    public Integer getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Integer airplaneId) {
        this.airplaneId = airplaneId;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public void setMaintenanceDate(LocalDate maintenanceDate) {
        this.maintenanceDate = maintenanceDate;
    }
}
