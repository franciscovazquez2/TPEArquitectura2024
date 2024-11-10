package org.example.microservtravel.repository;
import org.example.microservtravel.dto.ScooterReportDto;
import org.example.microservtravel.entity.Travel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TravelRepository extends MongoRepository<Travel,Long> {
    // Obtener total de kilómetros recorridos por un scooter específico
    //@Query(value = "{ 'id_scooter': ?0 }", fields = "{ 'kilometers': 1, 'pauseTime': ?1 }")
    @Aggregation(pipeline = {
            "{ $group: { _id: '$id_scooter', totalKilometers: { $sum: '$kilometers' }, totalUsageTime: { $sum: '$usageTime' }, totalPauseTime: { $sum: '$pauseTime' } } }",
            "{ $project: { idScooter: '$_id', totalKilometers: 1, totalUsageTime: 1, totalPauseTime: 1, _id: 0 } }"
    })
    List<ScooterReportDto> reporteScooterKilometrosTiempo();
    //List<Travel> reporteScooterPorKilometros (Long id_scooter, boolean includePause);
}
