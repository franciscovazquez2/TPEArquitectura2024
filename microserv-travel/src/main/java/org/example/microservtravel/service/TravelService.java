package org.example.microservtravel.service;
import org.example.microservtravel.dto.ScooterReportDto;
import org.example.microservtravel.dto.TravelScooterReportDto;
import org.example.microservtravel.entity.Travel;
import org.example.microservtravel.repository.TravelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public List<TravelScooterReportDto> reporteScooterPorKilometros(boolean includePause) {

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
    }
}
