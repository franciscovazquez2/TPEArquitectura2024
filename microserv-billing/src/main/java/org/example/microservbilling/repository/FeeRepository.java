package org.example.microservbilling.repository.FeeRepository;
import org.example.microservbilling.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeRepository extends JpaRepository<Fee,Long> {
}