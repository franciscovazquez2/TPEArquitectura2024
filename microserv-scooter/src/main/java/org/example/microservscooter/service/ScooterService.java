package org.example.microservscooter.service;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.error.exception.ScooterMaintenanceException;
import org.example.microservscooter.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScooterService {

    @Autowired
    private ScooterRepository scooterRepository;

    public List<Scooter> getAllScooter(){
        return scooterRepository.findAll();
    }

    public Scooter createScooter(Scooter newScooter){
        return scooterRepository.save(newScooter);
    }

    // VER DE DEVOLVER DTO
    public Optional<Scooter> getScooter(Long id){
        return scooterRepository.findById(id);
    }

    public void delete(Long id) {
        scooterRepository.deleteById(id);
    }

    public Optional<ScooterDTO> getScooterByMaintenance(Long id){
        return scooterRepository.getScooterByMaintenance(id);
    }

    //cambia el estado de mantenimiento y disponibilidad
    public Scooter startMaintenance(Long id){
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if(scooterOptional.isPresent()){
            Scooter scooter = scooterOptional.get();
            //chequear si ya se encuentra en mantenimiento o no
            if(scooter.isAvailable()&&!scooter.isMaintenance()){
                scooter.setAvailable(false);
                scooter.setMaintenance(true);
            }
            return scooterRepository.save(scooter);
        }
        throw new NoSuchElementException("monopatin no encontrado");
    }

    //cambia el estado de mantenimiento y disponibilidad
    public Scooter finishMaintenance(Long id){
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if(scooterOptional.isPresent()){
            Scooter scooter = scooterOptional.get();
            //chequear si ya se encuentra en mantenimiento o no
            if(!scooter.isAvailable() && scooter.isMaintenance()){
                scooter.setAvailable(true);
                scooter.setMaintenance(false);
            }else{
                throw new ScooterMaintenanceException("El scooter ya se encuentra en mantenimiento");
            }
            return scooterRepository.save(scooter);
        }
        throw new NotExistsException("El scooter no existe");
    }

    //ubicar scooter en parada
    public Scooter ubicarScooterEnParada(Long id, Long id_parada){
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);

        //buscar parada..preguntar si tiene lugar.persistir el id de monopatin o disminuir disponibilidad

        if(scooterOptional.isPresent()){
            Scooter scooter = scooterOptional.get();
            scooter.setIdParking(id_parada);
            return scooterRepository.save(scooter);
            //parada debe tambien guardar su estado

        }
        throw new NoSuchElementException("monopatin o parada no encontrada");
    }
}
