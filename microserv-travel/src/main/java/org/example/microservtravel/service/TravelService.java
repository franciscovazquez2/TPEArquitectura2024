package org.example.microservtravel.service;
import org.example.microservtravel.dto.KilometrosPorScooterDto;
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

    /*reporte de uso de monopatines por kilómetros para establecer si un monopatín requiere de mantenimiento.
     Este reporte debe poder configurarse para incluir (o no) los tiempos de pausa. */
    public KilometrosPorScooterDto reporteScooterPorKilometros(Long id_scooter, boolean includePause){

        List<Travel>travels = travelRepository.reporteScooterPorKilometros(id_scooter,includePause);
        long kilometers = 0;
        long usageTime = 0;

        for(Travel travel : travels){
            kilometers+=travel.getKilometers();

            if(includePause){
                usageTime+= travel.getUsageTime() + travel.getPauseTime();
            }else{
                usageTime+= travel.getUsageTime();
            }
        }
        return KilometrosPorScooterDto.builder()
                                      .scooterId(id_scooter)
                                      .totalDistance(kilometers)
                                      .totalUsageTime(usageTime)
                                      .includePause(includePause).build();

    }
}
