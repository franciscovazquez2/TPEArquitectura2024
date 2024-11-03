package org.example.microservscooter.repository;

import org.example.microservscooter.entity.Scooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScooterRepository extends JpaRepository<Scooter,Long> {
}
