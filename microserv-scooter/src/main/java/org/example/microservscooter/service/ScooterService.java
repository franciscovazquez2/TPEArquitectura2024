package org.example.microservscooter.service;

import org.example.microservscooter.client.ParkingClient;
import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.dto.parking.ParkingDto;
import org.example.microservscooter.dto.parking.ScootersActiveScootersInactiveDto;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.error.exception.NotExistsException;
import org.example.microservscooter.error.exception.NotFoundScooterException;
import org.example.microservscooter.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScooterService {

    @Autowired
    private ScooterRepository scooterRepository;

    @Autowired
    private ParkingClient parkingClient;

    //lista scooters
    public List<ScooterDTO> getAllScooter(){
            List<Scooter>scooterList= scooterRepository.findAll();
            List<ScooterDTO>result=new ArrayList<>();
            for(Scooter scooter: scooterList){
                ScooterDTO scooterDTO = ScooterDTO.builder()
                        .id_scooter(scooter.getId_scooter())
                        .latitude(scooter.getLatitude())
                        .longitude(scooter.getLongitude())
                        .kilometers(scooter.getKilometers())
                        .usageTime(scooter.getUsageTime())
                        .available(scooter.isAvailable())
                        .maintenance(scooter.isMaintenance())
                        .id_parking(scooter.getIdParking()).build();
                result.add(scooterDTO);
            }
            return result;
    }

    //crea monopatin
    public ScooterDTO createScooter(Scooter newScooter){
            Scooter scooter = scooterRepository.save(newScooter);

            return ScooterDTO.builder()
                    .id_scooter(scooter.getId_scooter())
                    .latitude(scooter.getLatitude())
                    .longitude(scooter.getLongitude())
                    .kilometers(scooter.getKilometers())
                    .usageTime(scooter.getUsageTime())
                    .available(scooter.isAvailable())
                    .maintenance(scooter.isMaintenance())
                    .id_parking(scooter.getIdParking()).build();
    }

    //monopatin por id
    public ScooterDTO getScooter(Long id){

            Optional<Scooter>scooterOptional= scooterRepository.findById(id);
            if(scooterOptional.isPresent()) {
                return ScooterDTO.builder()
                        .id_scooter(scooterOptional.get().getId_scooter())
                        .latitude(scooterOptional.get().getLatitude())
                        .longitude(scooterOptional.get().getLongitude())
                        .kilometers(scooterOptional.get().getKilometers())
                        .usageTime(scooterOptional.get().getUsageTime())
                        .available(scooterOptional.get().isAvailable())
                        .maintenance(scooterOptional.get().isMaintenance())
                        .id_parking(scooterOptional.get().getIdParking()).build();
            }else {
                throw new NotExistsException("El id: " + id+ " No existe");
            }
    }

    //elimina monopatin
    public ScooterDTO delete(Long id) {
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if(scooterOptional.isPresent()) {
            scooterRepository.deleteById(id);
            return ScooterDTO.builder() .id_scooter(scooterOptional.get().getId_scooter())
                                        .id_parking(scooterOptional.get().getIdParking())
                                        .available(scooterOptional.get().isAvailable())
                                        .kilometers(scooterOptional.get().getKilometers())
                                        .latitude(scooterOptional.get().getLatitude())
                                        .longitude(scooterOptional.get().getLongitude())
                                        .maintenance(scooterOptional.get().isMaintenance())
                                        .usageTime(scooterOptional.get().getUsageTime()).build();
        }else {
            throw new NotExistsException("El id que intentas eliminar no existe" + " ID: " +id);
        }
    }

    //monopatin por mantenimiento
    public Optional<ScooterDTO> getScooterByMaintenance(Long id){
        return scooterRepository.getScooterByMaintenance(id);
    }

    //cambia el estado de mantenimiento y disponibilidad
    public ScooterDTO startMaintenance(Long id){
            Optional<Scooter> scooterOptional = scooterRepository.findById(id);
            if(scooterOptional.isPresent()){
                Scooter scooter = scooterOptional.get();
                //chequear si ya se encuentra en mantenimiento o no
                if(scooter.isAvailable()&&!scooter.isMaintenance()){
                    scooter.setAvailable(false);
                    scooter.setMaintenance(true);
                }
                Scooter scooterResult= scooterRepository.save(scooter);
                return ScooterDTO.builder()
                        .id_scooter(scooterResult.getId_scooter())
                        .latitude(scooterResult.getLatitude())
                        .longitude(scooterResult.getLongitude())
                        .kilometers(scooterResult.getKilometers())
                        .usageTime(scooterResult.getUsageTime())
                        .available(scooterResult.isAvailable())
                        .maintenance(scooterResult.isMaintenance())
                        .id_parking(scooterResult.getIdParking()).build();
            }else {
                throw new NotExistsException("El Scooter con ID: " + id + " No existe");
            }
    }

    //cambia el estado de mantenimiento y disponibilidad
    public ScooterDTO finishMaintenance(Long id){
            Optional<Scooter> scooterOptional = scooterRepository.findById(id);
            if(scooterOptional.isPresent()){
                Scooter scooter = scooterOptional.get();
                //chequear si ya se encuentra en mantenimiento o no
                if(!scooter.isAvailable() && scooter.isMaintenance()){
                    scooter.setAvailable(true);
                    scooter.setMaintenance(false);
                }
                Scooter scooterResult= scooterRepository.save(scooter);
                return ScooterDTO.builder()
                        .id_scooter(scooterResult.getId_scooter())
                        .latitude(scooterResult.getLatitude())
                        .longitude(scooterResult.getLongitude())
                        .kilometers(scooterResult.getKilometers())
                        .usageTime(scooterResult.getUsageTime())
                        .available(scooterResult.isAvailable())
                        .maintenance(scooterResult.isMaintenance())
                        .id_parking(scooterResult.getIdParking()).build();
            }else {
                throw new NotExistsException("El Scooter con ID: " + id + " No existe");
            }
    }

    //ubicar scooter en parada
    public ScooterDTO ubicarScooterEnParada(Long id, Long id_parada){

        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if (!scooterOptional.isPresent()) {
            throw new NotExistsException("El Scooter con ID: " + id + " No existe");
        }

        ParkingDto parkingDto = parkingClient.findParkingBuyId(id_parada);
        if (parkingDto == null) {
            throw new NotExistsException("No existe la parada ID: " + id_parada);
        }

        if(!parkingDto.isAvailable()){
            throw new NotFoundScooterException("No esta disponible la parada ID: " + id_parada);
        }
        //buscar parada..preguntar si tiene lugar.persistir el id de monopatin o disminuir disponibilidad
            Scooter scooter = scooterOptional.get();
            scooter.setIdParking(id_parada);
            Scooter scooterResult = scooterRepository.save(scooter);

            //parada debe tambien guardar su estado
            ParkingDto modifiedParking = parkingClient.parkingChangeStatus(id_parada);
            return ScooterDTO.builder()
                    .id_scooter(scooterResult.getId_scooter())
                    .latitude(scooterResult.getLatitude())
                    .longitude(scooterResult.getLongitude())
                    .kilometers(scooterResult.getKilometers())
                    .usageTime(scooterResult.getUsageTime())
                    .available(scooterResult.isAvailable())
                    .maintenance(scooterResult.isMaintenance())
                    .id_parking(scooterResult.getIdParking()).build();
    }

    public ScootersActiveScootersInactiveDto getScooterByOperation(){
        int scooterInMaintenance = scooterRepository.getScootersInMaintenance();
        int scooterInOperation = scooterRepository.getScootersInOperation();
        return ScootersActiveScootersInactiveDto.builder()
                .cantScooterActive(scooterInOperation)
                .cantScooterInactive(scooterInMaintenance).build();
    }

    public List<ScooterDTO>getNearlyScooters(double latitude,double longitude,double distance){
        return scooterRepository.getNearlyScooters(latitude,longitude,distance);
    }

    public ScooterDTO startTrip(Long id) {
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if (!scooterOptional.isPresent()) {
            throw new NotExistsException("El Scooter con ID: " + id + " No existe");
        }
        Scooter scooter = scooterOptional.get();
        scooter.setAvailable(false);
        scooter.setStart(true);
        Scooter scooterResult = scooterRepository.save(scooter);
        return ScooterDTO.builder()
                .id_scooter(scooterResult.getId_scooter())
                .latitude(scooterResult.getLatitude())
                .longitude(scooterResult.getLongitude())
                .kilometers(scooterResult.getKilometers())
                .usageTime(scooterResult.getUsageTime())
                .available(scooterResult.isAvailable())
                .maintenance(scooterResult.isMaintenance())
                .id_parking(scooterResult.getIdParking()).build();
    }

    public ScooterDTO finishTrip(Long id) {
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);
        if (!scooterOptional.isPresent()) {
            throw new NotExistsException("El Scooter con ID: " + id + " No existe");
        }
        Scooter scooter = scooterOptional.get();
        scooter.setAvailable(true);
        scooter.setStart(false);
        Scooter scooterResult = scooterRepository.save(scooter);
        return ScooterDTO.builder()
                .id_scooter(scooterResult.getId_scooter())
                .latitude(scooterResult.getLatitude())
                .longitude(scooterResult.getLongitude())
                .kilometers(scooterResult.getKilometers())
                .usageTime(scooterResult.getUsageTime())
                .available(scooterResult.isAvailable())
                .maintenance(scooterResult.isMaintenance())
                .id_parking(scooterResult.getIdParking()).build();
    }
}
