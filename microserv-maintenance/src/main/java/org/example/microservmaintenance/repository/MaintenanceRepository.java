package org.example.microservmaintenance.repository;

import org.example.microservmaintenance.entity.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MaintenanceRepository extends JpaRepository<Maintenance,Long> {
}
