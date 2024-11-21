package org.example.microservtravel.repository;
import org.example.microservtravel.dto.ScooterReportDto;
import org.example.microservtravel.dto.ScooterReportXviajesDto;
import org.example.microservtravel.entity.Travel;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TravelRepository extends MongoRepository<Travel,String> {
    @Aggregation(pipeline = {
            "{ $group: { _id: '$id_scooter', totalKilometers: { $sum: '$kilometers' }, totalUsageTime: { $sum: '$usageTime' }, totalPauseTime: { $sum: '$pauseTime' } } }",
            "{ $project: { idScooter: '$_id', totalKilometers: 1, totalUsageTime: 1, totalPauseTime: 1, _id: 0 } }"
    })
    List<ScooterReportDto> reporteScooterKilometrosTiempo();

    @Aggregation(pipeline = {
            "{ $match: { $expr: { $eq: [ { $year: '$date' }, ?0 ] } } }", // Filtrar por año específico
            "{ $group: { " +
                    "_id: '$id_scooter', " +
                    "tripCount: { $sum: 1 }, " +
                    "totalKilometers: { $sum: '$kilometers' }, " +
                    "totalUsageTime: { $sum: '$usageTime' }, " +
                    "totalPauseTime: { $sum: '$pauseTime' } } }", // Agrupar y sumar
            "{ $match: { 'tripCount': { $gte: ?1 } } }" // Filtrar por mínimo de viajes
    })
    List<ScooterReportXviajesDto>reporteScooterConMasDeXkilometros (int year, int minTrips);
}
