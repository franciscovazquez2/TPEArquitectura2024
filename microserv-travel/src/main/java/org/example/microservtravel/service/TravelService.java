package org.example.microservtravel.service;
import org.example.microservtravel.dto.ScooterReportDto;
import org.example.microservtravel.dto.ScooterReportXviajesDto;
import org.example.microservtravel.dto.TravelDto;
import org.example.microservtravel.dto.TravelScooterReportDto;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.error.exception.NotExistsException;
import org.example.microservtravel.error.exception.NotFoundException;
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
    }

    //creacion de viaje
    public TravelDto createTravel(Travel newTravel){
            Travel travel = travelRepository.insert(newTravel);
            if(travel != null) {
                return TravelDto.builder()
                        .id_viaje(travel.getId_viaje())
                        .id_user(travel.getId_user())
                        .id_scooter(travel.getId_scooter())
                        .date(travel.getDate())
                        .price(travel.getPrice())
                        .kilometers(travel.getKilometers())
                        .usageTime(travel.getUsageTime())
                        .pauseTime(travel.getPauseTime())
                        .hasPauses(travel.isHasPauses()).build();
            }else {
                throw new NotFoundException("No se pudo crear el viaje requerido");
            }
    }

    //viaje por id
    public TravelDto getTravel(Long id){
            Optional<Travel> travelOptional = travelRepository.findById(id);
            if(travelOptional.isPresent()) {
                return TravelDto.builder()
                        .id_viaje(travelOptional.get().getId_viaje())
                        .id_user(travelOptional.get().getId_user())
                        .id_scooter(travelOptional.get().getId_scooter())
                        .date(travelOptional.get().getDate())
                        .price(travelOptional.get().getPrice())
                        .kilometers(travelOptional.get().getKilometers())
                        .usageTime(travelOptional.get().getUsageTime())
                        .pauseTime(travelOptional.get().getPauseTime())
                        .hasPauses(travelOptional.get().isHasPauses()).build();
            }else{
                throw new NotExistsException("El id:" +id + " ingresado no existe");
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
                reportResult.add(TravelScooterReportDto.builder()
                        .idScooter(report.getIdScooter())
                        .kilometers(report.getTotalKilometers())
                        .usageTime(usageTime)
                        .includePause(includePause)
                        .build());
            }
            return reportResult;
        }catch (Exception e){
            throw new NotFoundException("Error al generar el reporte");
        }
    }

    //reporte scooters en un año por cantidad de viajes
    public List<ScooterReportXviajesDto> reporteScooterConMasDeXkilometros(int year, int cantViajes){
        return travelRepository.reporteScooterConMasDeXkilometros(year,cantViajes);
    }
}
