package org.example.microservmaintenance.services;

import org.example.microservmaintenance.client.ScooterClient;
import org.example.microservmaintenance.dto.MaintenanceDTO;
import org.example.microservmaintenance.dto.scooter.ScooterDTO;
import org.example.microservmaintenance.entity.Maintenance;
import org.example.microservmaintenance.error.exception.NotExistsException;
import org.example.microservmaintenance.error.exception.ScooterMaintenanceException;
import org.example.microservmaintenance.http.response.MaintenanceScooterResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservmaintenance.repository.MaintenanceRepository;

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
        }else{
            throw new ScooterMaintenanceException("El scooter se encuentra en mantenimiento");
        }
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

    public void deleteMaintenance(Long id){
        maintenanceRepository.deleteById(id);
    }

    //finaliza el mantenimiento
    public MaintenanceDTO finishMaintenance(Long id) {
        Optional<Maintenance> maintenanceOptional = maintenanceRepository.findById(id);
        if(maintenanceOptional.isPresent()){
            Maintenance maintenance = maintenanceOptional.get();
            if(!maintenance.isFinalizado()){
                scooterClient.finishMaintenance(maintenance.getIdScooter());
                maintenance.setFinalizado(true);
                Maintenance result = maintenanceRepository.save(maintenance);
                return MaintenanceDTO.builder()
                        .id(result.getId())
                        .id_scooter(result.getIdScooter())
                        .fecha_mantenimiento(result.getFecha_inicio())
                        .finalizado(result.isFinalizado()).build();
            }

        }
        throw new NotExistsException("el id no existe");
    }
}

