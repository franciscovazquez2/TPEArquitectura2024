package org.example.microservparking.services;

import org.example.microservparking.entity.Parking;
import org.example.microservparking.error.exception.ExistException;
import org.example.microservparking.error.exception.NotExistsException;
import org.example.microservparking.error.exception.ParkingFullExection;
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
    public Parking createParking(Parking newParking) throws ExistException{
        List<Parking> parkings = parkingRepository.findAll();
        parkings.forEach(parking -> { if((parking.getLatitude() == newParking.getLatitude())
                                            && (parking.getLongitude() == newParking.getLongitude())) {
                        throw new ExistException("La parada que quieres ingresar es existente ");}});
        return parkingRepository.save(newParking);
    }

    //elimina una parada
    public Optional<Parking> deleteParking(Long id) throws NotExistsException{
        Optional<Parking> parking = parkingRepository.findById(id);
        if(!parking.isPresent()) {
            throw new NotExistsException("La parada no existe. ID: " + id);
        }
        parkingRepository.deleteById(id);
        return parking;
    }

    //ocupa un lugar en la parada
    public Parking ocuparEstacionamiento(Long id) throws ParkingFullExection,NotExistsException{
        Optional<Parking>parkingOptional=parkingRepository.findById(id);
        if(parkingOptional.isPresent()){
            Parking parking = parkingOptional.get();
            if(parking.isAvailable()){
                parking.decreaseCapacity();
                return parkingRepository.save(parking);
            }
            throw  new ParkingFullExection("estacionamiento lleno");
        }
        throw new NotExistsException("no existe parking con id: " + id);
    }

    //libera un lugar en la parada
    public Parking liberarEstacionamiento(Long id) throws NotExistsException{
        Optional<Parking>parkingOptional=parkingRepository.findById(id);
        if(parkingOptional.isPresent()){
            Parking parking = parkingOptional.get();
            parking.incrementCapacity();
            return parkingRepository.save(parking);
        }
        throw new NotExistsException("no existe parking con ID: " + id);
    }
}