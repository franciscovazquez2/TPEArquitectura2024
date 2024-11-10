package org.example.microservscooter.service;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
