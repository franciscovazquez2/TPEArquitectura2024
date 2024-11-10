package org.example.microservscooter.service;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.example.microservscooter.repository.ScooterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ScooterService {

    @Autowired
    private ScooterRepository scooterRepository;

    //lista scooters
    public List<ScooterDTO> getAllScooter(){
        try{
            List<Scooter>scooterList= scooterRepository.findAll();
            List<ScooterDTO>result=new ArrayList<>();
            for(Scooter scooter: scooterList){
                ScooterDTO scooterDTO = ScooterDTO.builder()
                        .id_scooter(scooter.getId_scooter())
                        .latitude(scooter.getLatitude())
                        .longitude(scooter.getLongitude())
                        .kilometers(scooter.getKilometers())
                        .usageTime(scooter.getUsageTime())
                        .maintenance(scooter.isMaintenance()).build();
                result.add(scooterDTO);
            }
            return result;
        }catch (Exception e){
            throw new NoSuchElementException("error al listar los monopatines");
        }
    }

    //crea monopatin
    public ScooterDTO createScooter(Scooter newScooter){
        try {
            Scooter scooter = scooterRepository.save(newScooter);
            ScooterDTO scooterDTO = ScooterDTO.builder()
                    .id_scooter(scooter.getId_scooter())
                    .latitude(scooter.getLatitude())
                    .longitude(scooter.getLongitude())
                    .kilometers(scooter.getKilometers())
                    .usageTime(scooter.getUsageTime())
                    .maintenance(scooter.isMaintenance()).build();
            return scooterDTO;
        }catch (Exception e){
            throw new NoSuchElementException("error al crear el monopatin");
        }
    }

    //monopatin por id
    public ScooterDTO getScooter(Long id){
        try{
            Optional<Scooter>scooterOptional= scooterRepository.findById(id);
            Scooter scooter = scooterOptional.get();
            ScooterDTO scooterDTO = ScooterDTO.builder()
                    .id_scooter(scooter.getId_scooter())
                    .latitude(scooter.getLatitude())
                    .longitude(scooter.getLongitude())
                    .kilometers(scooter.getKilometers())
                    .usageTime(scooter.getUsageTime())
                    .maintenance(scooter.isMaintenance()).build();
            return scooterDTO;
        }catch (Exception e){
            throw new NoSuchElementException("error al buscar monopatin id: "+id);
        }
    }

    //elimina monopatin
    public void delete(Long id) {
        scooterRepository.deleteById(id);
    }

    //monopatin por mantenimiento
    public Optional<ScooterDTO> getScooterByMaintenance(Long id){
        return scooterRepository.getScooterByMaintenance(id);
    }

    //cambia el estado de mantenimiento y disponibilidad
    public ScooterDTO startMaintenance(Long id){
        try{
            Optional<Scooter> scooterOptional = scooterRepository.findById(id);
            if(scooterOptional.isPresent()){
                Scooter scooter = scooterOptional.get();
                //chequear si ya se encuentra en mantenimiento o no
                if(scooter.isAvailable()&&!scooter.isMaintenance()){
                    scooter.setAvailable(false);
                    scooter.setMaintenance(true);
                }
                Scooter scooterResult= scooterRepository.save(scooter);
                ScooterDTO scooterDTO = ScooterDTO.builder()
                        .id_scooter(scooterResult.getId_scooter())
                        .latitude(scooterResult.getLatitude())
                        .longitude(scooterResult.getLongitude())
                        .kilometers(scooterResult.getKilometers())
                        .usageTime(scooterResult.getUsageTime())
                        .maintenance(scooterResult.isMaintenance()).build();
                return scooterDTO;
            }
            throw new NoSuchElementException("monopatin no encontrado");
        } catch (Exception e) {
            throw new NoSuchElementException("no se puede iniciar mantenimiento");
        }
    }

    //cambia el estado de mantenimiento y disponibilidad
    public ScooterDTO finishMaintenance(Long id){
        try{
            Optional<Scooter> scooterOptional = scooterRepository.findById(id);
            if(scooterOptional.isPresent()){
                Scooter scooter = scooterOptional.get();
                //chequear si ya se encuentra en mantenimiento o no
                if(!scooter.isAvailable() && scooter.isMaintenance()){
                    scooter.setAvailable(true);
                    scooter.setMaintenance(false);
                }
                Scooter scooterResult= scooterRepository.save(scooter);
                ScooterDTO scooterDTO = ScooterDTO.builder()
                        .id_scooter(scooterResult.getId_scooter())
                        .latitude(scooterResult.getLatitude())
                        .longitude(scooterResult.getLongitude())
                        .kilometers(scooterResult.getKilometers())
                        .usageTime(scooterResult.getUsageTime())
                        .maintenance(scooterResult.isMaintenance()).build();
                return scooterDTO;
            }
            throw new NoSuchElementException("monopatin no encontrado");
        } catch (Exception e) {
            throw new NoSuchElementException("no se puede finalizar mantenimiento");
        }
    }

    //ubicar scooter en parada
    public ScooterDTO ubicarScooterEnParada(Long id, Long id_parada){
        Optional<Scooter> scooterOptional = scooterRepository.findById(id);

        //buscar parada..preguntar si tiene lugar.persistir el id de monopatin o disminuir disponibilidad
        if(scooterOptional.isPresent()){
            Scooter scooter = scooterOptional.get();
            scooter.setIdParking(id_parada);
            Scooter scooterResult=scooterRepository.save(scooter);
            ScooterDTO scooterDTO = ScooterDTO.builder()
                    .id_scooter(scooterResult.getId_scooter())
                    .latitude(scooterResult.getLatitude())
                    .longitude(scooterResult.getLongitude())
                    .kilometers(scooterResult.getKilometers())
                    .usageTime(scooterResult.getUsageTime())
                    .maintenance(scooterResult.isMaintenance()).build();
            return scooterDTO;
            //parada debe tambien guardar su estado

        }
        throw new NoSuchElementException("monopatin o parada no encontrada");
    }
}
