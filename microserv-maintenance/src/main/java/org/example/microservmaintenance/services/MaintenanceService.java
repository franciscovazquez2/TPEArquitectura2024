package org.example.microservmaintenance.services;

import org.example.microservmaintenance.client.ScooterClient;
import org.example.microservmaintenance.dto.MaintenanceDTO;
import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.error.exception.NotExistsException;
import org.example.microservmaintenance.http.response.MaintenanceScooterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservmaintenance.repository.MaintenanceRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public MaintenanceDTO createMaintenance(Maintenance newMaintenance){
        ScooterDTO scooterDTO = scooterClient.findScooterBuyId(newMaintenance.getIdScooter());
        if(scooterDTO==null||scooterDTO.isEmpty()){
            throw new NotExistsException("El monopatin no existe");
        }
        if(!scooterDTO.isMaintenence()){
            Maintenance m1 = maintenanceRepository.save(newMaintenance);
            ScooterDTO scooterDto = scooterClient.startMaintenance(newMaintenance.getIdScooter());
            return MaintenanceDTO.builder().id(m1.getId()).id_scooter(m1.getIdScooter())
                    .fecha_mantenimiento(m1.getFecha_inicio())
                    .finalizado(m1.isFinalizado()).build();
        }
        return MaintenanceDTO.builder().build();
    }

    public MaintenanceDTO finishMaintenance(Long idMaintenance){
        Optional<Maintenance> maintenanceOptional = maintenanceRepository.findById(idMaintenance);
        if(!maintenanceOptional.isPresent()){
            throw new NotExistsException("no existe mantenimiento con id: "+idMaintenance);
        }
        Maintenance maintenance = maintenanceOptional.get();
        if(!maintenance.isFinalizado()){
            maintenance.setFinalizado(true);
            Maintenance maintenanceResult = maintenanceRepository.save(maintenance);
            ScooterDTO scooterDto = scooterClient.finishMaintenance(maintenance.getIdScooter());
            return MaintenanceDTO.builder().id(maintenanceResult.getId())
                    .id_scooter(maintenanceResult.getIdScooter())
                    .fecha_mantenimiento(maintenanceResult.getFecha_inicio())
                    .finalizado(maintenanceResult.isFinalizado()).build();
        }
        throw  new NotExistsException("el matenimiento ya se encuentra finalizado");

    }

    public MaintenanceScooterResponse getMaintenance(Long id) {

        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);

        if(!maintenance.isPresent()){
           throw new NotExistsException("El id ingresado no existe");
        }

        ScooterDTO scooterDTO = scooterClient.findScooterBuyId(maintenance.get().getIdScooter());
        return MaintenanceScooterResponse.builder()
                .id(maintenance.get().getId())
                .scooter(scooterDTO)
                .build();
    }
}

