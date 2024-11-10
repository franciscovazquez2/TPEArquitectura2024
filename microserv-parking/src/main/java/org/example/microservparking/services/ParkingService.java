package org.example.microservparking.services;

import org.example.microservparking.dto.ParkingDto;
import org.example.microservparking.entity.Parking;
import org.example.microservparking.error.exception.ExistException;
import org.example.microservparking.error.exception.NotExistsException;
import org.example.microservparking.error.exception.ParkingFullExection;
import org.example.microservparking.repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service ("ParkingService")
public class ParkingService {

    @Autowired
    private ParkingRepository parkingRepository;

    //Lista de paradas
    public List<ParkingDto> getAllParkings(){
        List<Parking>parkingList=parkingRepository.findAll();
        List<ParkingDto>result = new ArrayList<>();
        for(Parking parking:parkingList){
            result.add(ParkingDto.builder()
                    .id(parking.getId())
                    .latitude(parking.getLatitude())
                    .longitude(parking.getLongitude())
                    .capacity(parking.getCapacity())
                    .actualCapacity(parking.getActualCapacity())
                    .available(parking.isAvailable())
                    .build());
        }
        return result;
    }

    //parada por id
    public ParkingDto getParking(Long id) {
        Optional<Parking> parkingOptional = parkingRepository.findById(id);
        Parking parking = parkingOptional.get();
        return ParkingDto.builder()
                .id(parking.getId())
                .latitude(parking.getLatitude())
                .longitude(parking.getLongitude())
                .capacity(parking.getCapacity())
                .actualCapacity(parking.getActualCapacity())
                .available(parking.isAvailable()).build();
    }

    //Crea una parada
    public ParkingDto createParking(Parking newParking) throws ExistException{
        List<Parking> parkings = parkingRepository.findAll();
        parkings.forEach(parking -> { if((parking.getLatitude() == newParking.getLatitude())
                                            && (parking.getLongitude() == newParking.getLongitude())) {
                        throw new ExistException("La parada que quieres ingresar es existente ");}});
        Parking parkingResult = parkingRepository.save(newParking);
        return ParkingDto.builder()
                .id(parkingResult.getId())
                .latitude(parkingResult.getLatitude())
                .longitude(parkingResult.getLongitude())
                .capacity(parkingResult.getCapacity())
                .actualCapacity(parkingResult.getActualCapacity())
                .available(parkingResult.isAvailable()).build();
    }

    //elimina una parada
    public ParkingDto deleteParking(Long id) throws NotExistsException{
        Optional<Parking> parkingOptional = parkingRepository.findById(id);
        if(!parkingOptional.isPresent()) {
            throw new NotExistsException("La parada no existe. ID: " + id);
        }
        Parking parking=parkingOptional.get();
        parkingRepository.deleteById(id);
        return ParkingDto.builder()
                .id(parking.getId())
                .latitude(parking.getLatitude())
                .longitude(parking.getLongitude())
                .capacity(parking.getCapacity())
                .actualCapacity(parking.getActualCapacity())
                .available(parking.isAvailable()).build();
    }

    //ocupa un lugar en la parada
    public ParkingDto ocuparEstacionamiento(Long id) throws ParkingFullExection,NotExistsException{
        Optional<Parking>parkingOptional=parkingRepository.findById(id);
        if(parkingOptional.isPresent()){
            Parking parking = parkingOptional.get();
            if(parking.isAvailable()){
                parking.decreaseCapacity();
                Parking parkingResult= parkingRepository.save(parking);
                return ParkingDto.builder()
                        .id(parkingResult.getId())
                        .latitude(parkingResult.getLatitude())
                        .longitude(parkingResult.getLongitude())
                        .capacity(parkingResult.getCapacity())
                        .actualCapacity(parkingResult.getActualCapacity())
                        .available(parkingResult.isAvailable()).build();
            }
            throw  new ParkingFullExection("estacionamiento lleno");
        }
        throw new NotExistsException("no existe parking con id: " + id);
    }

    //libera un lugar en la parada
    public ParkingDto liberarEstacionamiento(Long id) throws NotExistsException{
        Optional<Parking>parkingOptional=parkingRepository.findById(id);
        if(parkingOptional.isPresent()){
            Parking parking = parkingOptional.get();
            parking.incrementCapacity();
            Parking parkingResult= parkingRepository.save(parking);
            return ParkingDto.builder()
                    .id(parkingResult.getId())
                    .latitude(parkingResult.getLatitude())
                    .longitude(parkingResult.getLongitude())
                    .capacity(parkingResult.getCapacity())
                    .actualCapacity(parkingResult.getActualCapacity())
                    .available(parkingResult.isAvailable()).build();
        }
        throw new NotExistsException("no existe parking con ID: " + id);
    }
}