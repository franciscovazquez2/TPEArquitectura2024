package org.example.microservtravel.repository;
import org.example.microservtravel.dto.KilometrosPorScooterDto;
import org.example.microservtravel.entity.Travel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface TravelRepository extends MongoRepository<Travel,Long> {

    // Obtener total de kilómetros recorridos por un scooter específico
    @Query(value = "{ 'id_scooter': ?0 }", fields = "{ 'kilometers': 1, 'pauseTime': ?1 }")
    List<Travel> reporteScooterPorKilometros (Long id_scooter, boolean includePause);

}
