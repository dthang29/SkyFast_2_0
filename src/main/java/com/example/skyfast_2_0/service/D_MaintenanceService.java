package com.example.skyfast_2_0.service;

import com.example.skyfast_2_0.dto.D_MaintenanceDTO;
import com.example.skyfast_2_0.entity.Airplane;
import com.example.skyfast_2_0.entity.Maintenance;
import com.example.skyfast_2_0.repository.D_MaintenanceRepository;
import com.example.skyfast_2_0.repository.D_PlaneCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class D_MaintenanceService {

    @Autowired
    private D_MaintenanceRepository DMaintenanceRepository;

    @Autowired
    private D_PlaneCategoryRepository airplaneRepository;

    public boolean addMaintenance(D_MaintenanceDTO request) {
        Optional<Airplane> airplaneOpt = airplaneRepository.findById(request.getAirplaneId());

        if (airplaneOpt.isPresent()) {
            Airplane airplane = airplaneOpt.get();

            // Tạo mới Maintenance
            Maintenance maintenance = new Maintenance();
            maintenance.setDescription(request.getDescription());
            maintenance.setDuration(request.getDuration());
            maintenance.setMaintenanceDate(LocalDate.now()); // Ngày hiện tại
            maintenance.setMaintenanceStatus("Unprocessed");
            maintenance.setAirplane(airplane);
            DMaintenanceRepository.save(maintenance);

            // Cập nhật trạng thái máy bay thành "Maintaining"
            airplane.setAirplaneStatus("Maintaining");
            airplaneRepository.save(airplane);

            return true;
        }
        return false;
    }

    public List<Maintenance> getMaintenanceByAirplaneId(Integer airplaneId) {
        return DMaintenanceRepository.findByAirplaneIdSorted(airplaneId);
    }

    public List<Maintenance> getAllMaintenanceList() {
        return DMaintenanceRepository.findAll();
    }

    public List<Maintenance> searchMaintenancee(String status, LocalDate fromDate, LocalDate toDate) {
        return DMaintenanceRepository.findByCriteria(status, fromDate, toDate);
    }

    public Maintenance updateMaintenance(Integer id, String status, String description, int duration) {
        Optional<Maintenance> optionalMaintenance = DMaintenanceRepository.findById(id);
        if (optionalMaintenance.isPresent()) {
            Maintenance maintenance = optionalMaintenance.get();
            maintenance.setMaintenanceStatus(status);
            maintenance.setDescription(description);
            maintenance.setDuration(duration);
            if ("Processed".equals(status)) {
                maintenance.getAirplane().setAirplaneStatus("Waiting");
                airplaneRepository.save(maintenance.getAirplane()); // Cập nhật máy bay
            }
            return DMaintenanceRepository.save(maintenance);
        }
        return null;
    }
    public List<Maintenance> searchMaintenance(Integer airplaneId, String status, LocalDate fromDate, LocalDate toDate) {
        return DMaintenanceRepository.searchMaintenance(airplaneId,status, fromDate, toDate);
    }

    public Optional<Maintenance> getMaintenanceById(Integer id) {
        return DMaintenanceRepository.findById(id);
    }

}

