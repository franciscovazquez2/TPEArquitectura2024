package org.example.microservscooter.repository;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScooterRepository extends JpaRepository<Scooter,Long> {

    @Query("SELECT new org.example.microservscooter.dto.ScooterDTO(s.id_scooter, s.latitude, s.longitude, s.kilometers, s.usageTime, s.available, s.maintenance,s.idParking) "+
            " FROM Scooter s"+
            " WHERE s.id_scooter = :id")
    Optional<ScooterDTO> getScooterByMaintenance(@Param("id") Long id);

    @Query("SELECT count(s.id_scooter)from Scooter s where s.maintenance=true")
    int getScootersInMaintenance();

    @Query("SELECT count(s.id_scooter)from Scooter s where s.maintenance=false")
    int getScootersInOperation();

    @Query("SELECT new org.example.microservscooter.dto.ScooterDTO(s.id_scooter, s.latitude, s.longitude, s.kilometers, s.usageTime, s.available, s.maintenance,s.idParking) FROM Scooter s " +
            "WHERE s.available = true AND s.maintenance = false " +
            "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(s.latitude)) * " +
            "cos(radians(s.longitude) - radians(:longitude)) + sin(radians(:latitude)) * " +
            "sin(radians(s.latitude)))) < :distance")
    List<ScooterDTO> getNearlyScooters(@Param("latitude") double latitude,
                                       @Param("longitude") double longitude,
                                       @Param("distance") double distance);

}
