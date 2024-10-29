package org.example.microservmaintenance.services;

import org.example.microservmaintenance.entity.Maintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservmaintenance.repository.MaintenanceRepository;

import java.util.List;
import java.util.Optional;

@Service ("MaintenanceService")
public class MaintenanceService {

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    //lista todos los mantenimientos
    public List<Maintenance> getAllMaintenances(){
        return maintenanceRepository.findAll();
    }

    //devuelve un mantenimiento por id
    public Optional<Maintenance> getMaintenance(Long id){
        return maintenanceRepository.findById(id);
    }

    //crea un registro de mantenimiento
    public Maintenance createMaintenance(Maintenance newMaintenance){
        return maintenanceRepository.save(newMaintenance);
    }

}

