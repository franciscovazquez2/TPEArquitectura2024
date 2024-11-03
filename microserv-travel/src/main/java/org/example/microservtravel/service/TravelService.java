package org.example.microservtravel.service;
import org.example.microservtravel.dto.TravelDTO;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TravelService {
    @Autowired
    private TravelRepository travelRepository;

    // DEVOLVER UNA LISTA DE DTO

    public List<Travel> getAllTravels(){
        return travelRepository.findAll();
    }

    public Travel createTravel(Travel newTravel){
        return travelRepository.insert(newTravel);
    }

    // VER DE DEVOLVER DTO
    public Optional<Travel> getTravel(Long id){
        return travelRepository.findById(id);
    }

    public void delete(Long id) throws EmptyResultDataAccessException,Exception {
            travelRepository.deleteById(id);
    }
}
