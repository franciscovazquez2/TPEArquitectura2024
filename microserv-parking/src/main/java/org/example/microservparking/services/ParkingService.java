package org.example.microservparking.services;

import org.example.microservparking.entity.Parking;
import org.example.microservparking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service ("ParkingService")
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    //Lista de todas las paradas
    public List<Parking> getAllParkings(){return parkingRepository.findAll();}

    //Lista de una parada por id
    public Optional<Parking> getParking(Long id) {return parkingRepository.findById(id);}

    //Crea un registro de parada
    public Parking createParking(Parking newParking){
        return parkingRepository.save(newParking);
    }

    //elimina una parada
    public void deleteParking(Long id){
        parkingRepository.deleteById(id);
    }

    //ocupa un lugar en la parada
    public Parking ocuparEstacionamiento(Long id){
        Optional<Parking>parkingOptional=parkingRepository.findById(id);
        if(parkingOptional.isPresent()){
            Parking parking = parkingOptional.get();
            if(parking.isAvailable()){
                parking.setCapacity(1);
            }
        }
    }
    //libera un lugar en la parada
    public Parking liberarEstacionamiento(Long id){

    }
}