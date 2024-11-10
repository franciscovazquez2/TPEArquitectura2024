package org.example.microservtravel.service;
import org.example.microservtravel.dto.ScooterReportDto;
import org.example.microservtravel.dto.TravelDto;
import org.example.microservtravel.dto.TravelScooterReportDto;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TravelService {

    @Autowired
    private TravelRepository travelRepository;

    //lista completa de viajes
    public List<TravelDto> getAllTravels(){
        try{
            List<Travel>travels= travelRepository.findAll();
            List<TravelDto>result=new ArrayList<>();
            for(Travel travel:travels){
                TravelDto travelDto = TravelDto.builder()
                        .id_viaje(travel.getId_viaje())
                        .id_user(travel.getId_user())
                        .id_scooter(travel.getId_scooter())
                        .date(travel.getDate())
                        .price(travel.getPrice())
                        .kilometers(travel.getKilometers())
                        .usageTime(travel.getUsageTime())
                        .pauseTime(travel.getPauseTime())
                        .hasPauses(travel.isHasPauses()).build();
                result.add(travelDto);
            }
            return result;
        }catch(Exception e){
            throw new NoSuchElementException("No se puede listar los viajes");
        }

    }

    //creacion de viaje
    public TravelDto createTravel(Travel newTravel){
        try {
            Travel travel = travelRepository.insert(newTravel);
            TravelDto travelDto = TravelDto.builder()
                    .id_viaje(travel.getId_viaje())
                    .id_user(travel.getId_user())
                    .id_scooter(travel.getId_scooter())
                    .date(travel.getDate())
                    .price(travel.getPrice())
                    .kilometers(travel.getKilometers())
                    .usageTime(travel.getUsageTime())
                    .pauseTime(travel.getPauseTime())
                    .hasPauses(travel.isHasPauses()).build();
            return travelDto;
        }catch (Exception e){
            throw new NoSuchElementException("Error al crear viaje");
        }
    }

    //viaje por id
    public TravelDto getTravel(Long id){
        try {
            Optional<Travel> travelOptional = travelRepository.findById(id);
            Travel travel = travelOptional.get();
            TravelDto travelDto = TravelDto.builder()
                    .id_viaje(travel.getId_viaje())
                    .id_user(travel.getId_user())
                    .id_scooter(travel.getId_scooter())
                    .date(travel.getDate())
                    .price(travel.getPrice())
                    .kilometers(travel.getKilometers())
                    .usageTime(travel.getUsageTime())
                    .pauseTime(travel.getPauseTime())
                    .hasPauses(travel.isHasPauses()).build();
            return travelDto;
        }catch (Exception e ){
            throw new NoSuchElementException("error al buscar el viaje con id: "+id);
        }
    }

    //eliminacion de viaje
    public void delete(Long id) throws EmptyResultDataAccessException,Exception {
            try {
                travelRepository.deleteById(id);
            }catch (Exception e){
                throw new NoSuchElementException("no se puede eliminar viaje id: "+id);
            }
    }

    //reporte por kilometros, por tiempo , con y sin pausa
    public List<TravelScooterReportDto> reporteScooterPorKilometros(boolean includePause) {
        try {
            List<ScooterReportDto> reportesPorScooter = travelRepository.reporteScooterKilometrosTiempo();
            List<TravelScooterReportDto> reportResult = new ArrayList<>();

            for(ScooterReportDto report : reportesPorScooter){
                long usageTime;
                if(includePause){
                    usageTime=report.getTotalUsageTime()+report.getTotalPauseTime();
                }else {
                    usageTime=report.getTotalUsageTime();
                }
                TravelScooterReportDto reportDto = TravelScooterReportDto.builder()
                        .idScooter(report.getIdScooter())
                        .kilometers(report.getTotalKilometers())
                        .usageTime(usageTime)
                        .includePause(includePause)
                        .build();

                reportResult.add(reportDto);
            }
            return reportResult;
        }catch (Exception e){
            throw new NoSuchElementException("Error al generar el reporte");
        }
    }

}
