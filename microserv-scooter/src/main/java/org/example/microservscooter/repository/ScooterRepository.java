package org.example.microservscooter.repository;

import org.example.microservscooter.dto.ScooterDTO;
import org.example.microservscooter.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ScooterRepository extends JpaRepository<Scooter,Long> {

    @Query("SELECT new org.example.microservscooter.dto.ScooterDTO(s.id_scooter, s.latitude, s.longitude, s.kilometers, s.usageTime, s.available, s.maintenance) "+
            " FROM Scooter s"+
            " WHERE s.id_scooter = :id")
    Optional<ScooterDTO> getScooterByMaintenance(@Param("id") Long id);

    @Query("SELECT count(s.id_scooter)from Scooter s where s.maintenance=true")
    int getScootersInMaintenance();

    @Query("SELECT count(s.id_scooter)from Scooter s where s.maintenance=false")
    int getScootersInOperation();
}
