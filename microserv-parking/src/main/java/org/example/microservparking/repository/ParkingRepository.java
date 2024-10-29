package org.example.microservparking.repository;

import org.example.microservparking.entity.Parking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingRepository extends JpaRepository<Parking,Long> {

}