package com.example.skyfast_2_0.repository;

import com.example.skyfast_2_0.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface D_MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    @Query("SELECT m FROM Maintenance m WHERE m.airplane.id = :airplaneId ORDER BY m.maintenanceDate DESC, m.id DESC")
    List<Maintenance> findByAirplaneIdSorted(@Param("airplaneId") Integer airplaneId);

    @Query("SELECT m FROM Maintenance m WHERE " +
            "(:airplaneId IS NULL OR m.airplane.id = :airplaneId) AND " +  // Lọc theo airplaneId
            "(:status IS NULL OR :status = '' OR m.maintenanceStatus = :status) AND " +
            "(:fromDate IS NULL OR m.maintenanceDate >= :fromDate) AND " +
            "(:toDate IS NULL OR m.maintenanceDate <= :toDate) " +
            "ORDER BY m.maintenanceDate DESC, m.id DESC")  // Sắp xếp theo ngày và ID
    List<Maintenance> searchMaintenance(@Param("airplaneId") Integer airplaneId,
                                        @Param("status") String status,
                                        @Param("fromDate") LocalDate fromDate,
                                        @Param("toDate") LocalDate toDate);

    @Query("SELECT m FROM Maintenance m WHERE "
            + "(:status IS NULL OR :status = '' OR m.maintenanceStatus = :status) "
            + "AND (:fromDate IS NULL OR m.maintenanceDate >= :fromDate) "
            + "AND (:toDate IS NULL OR m.maintenanceDate <= :toDate) "
            + "AND (:airplaneName IS NULL OR :airplaneName = '' OR m.airplane.airplaneName LIKE %:airplaneName%) "
            + "ORDER BY m.maintenanceDate DESC, m.id DESC")
    List<Maintenance> findByCriteria(@Param("status") String status,
                                     @Param("fromDate") LocalDate fromDate,
                                     @Param("toDate") LocalDate toDate,
                                     @Param("airplaneName") String airplaneName);



}


