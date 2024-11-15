package org.example.microservbilling.services;
import org.example.microservbilling.dto.BillingDto;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.example.microservbilling.error.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservbilling.repository.BillingRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service ("BillingService")
public class BillingService {
    @Autowired
    private BillingRepository billingRepository;

    //lista de todas las facturas
    public List<BillingDto> getAllBillings() {
        List<Billing> billingList = billingRepository.findAll();
        List<BillingDto> result = new ArrayList<>();
        for (Billing billing : billingList) {
            BillingDto billingDto = BillingDto.builder()
                    .id(billing.getId())
                    .fechaEmision(billing.getFechaEmision())
                    .idReserva(billing.getIdReserva())
                    .idUsuario(billing.getIdUsuario())
                    .montoTotal(billing.getMontoTotal()).build();
            result.add(billingDto);
        }
        return result;
    }

    //devuelve una factura
    public BillingDto getBilling(Long id) {
        try {
            Optional<Billing> billingOptional = billingRepository.findById(id);
            Billing billing = billingOptional.get();
            return BillingDto.builder()
                    .id(billing.getId())
                    .idReserva(billing.getIdReserva())
                    .idUsuario(billing.getIdUsuario())
                    .fechaEmision(billing.getFechaEmision())
                    .montoTotal(billing.getMontoTotal())
                    .build();
        } catch (RuntimeException e) {
            throw new NotFoundException("no se encontro factura id: " + id);
        }
    }

    //crea una factura
    public BillingDto createBilling(Billing newBilling){
        Billing billing =billingRepository.save(newBilling);
        return BillingDto.builder()
                .id(billing.getId())
                .idUsuario(billing.getIdUsuario())
                .idReserva(billing.getIdReserva())
                .fechaEmision(billing.getFechaEmision())
                .montoTotal(billing.getMontoTotal()).build();
    }

    public TotalFacturadoDto reporteTotalFacturadoEnFecha(int year, int startMonth,int endMonth){
        return billingRepository.reporteTotalFacturadoEnFecha(year,startMonth,endMonth);
    }

    public void deleteBilling (Long id){
        billingRepository.deleteById(id);
    }
}