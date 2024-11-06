package org.example.microservmaintenance.services;

import org.example.microservmaintenance.client.ScooterClient;
import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.http.response.MaintenanceScooterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservmaintenance.repository.MaintenanceRepository;

import java.util.List;

@Service ("MaintenanceService")
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private ScooterClient scooterClient;

    //lista todos los mantenimientos
    public List<Maintenance> getAllMaintenances(){
        return maintenanceRepository.findAll();
    }


    //crea un registro de mantenimiento
    public Maintenance createMaintenance(Maintenance newMaintenance){
        return maintenanceRepository.save(newMaintenance);
    }


    public MaintenanceScooterResponse getMaintenance(Long id){

        Maintenance maintenance = maintenanceRepository.getReferenceById(id);

        ScooterDTO scooterDTO = scooterClient.findScooterBuyId(maintenance.getIdScooter());
        return MaintenanceScooterResponse.builder()
                .id(maintenance.getId())
                .scooter(scooterDTO)
                .build();
    }
}

