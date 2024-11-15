package org.example.microservbilling.repository;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillingRepository extends JpaRepository<Billing,Long> {
    /*
    @Query("SELECT new org.example.microservbilling.dto.TotalFacturadoDto(SUM(b.montoTotal), :year, :startMonth, :endMonth) " +
            "FROM Billing b " +
            "WHERE YEAR(b.fechaEmision) = :year " +
            "AND MONTH(b.fechaEmision) BETWEEN :startMonth AND :endMonth")
    TotalFacturadoDto reporteTotalFacturadoEnFecha(@Param("year") int year, @Param("startMonth") int startMonth, @Param("endMonth") int endMonth);
     */

    @Query("SELECT b " +
            "FROM Billing b " +
            "WHERE YEAR(b.fechaEmision) = :year " +
            "AND MONTH(b.fechaEmision) BETWEEN :startMonth AND :endMonth")
    List<Billing> reporteTotalFacturadoEnFecha(@Param("year") int year, @Param("startMonth") int startMonth, @Param("endMonth") int endMonth);

}