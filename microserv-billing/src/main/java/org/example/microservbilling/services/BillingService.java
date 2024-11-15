package org.example.microservbilling.services;
import org.example.microservbilling.dto.BillingDto;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.example.microservbilling.error.exception.NotExistsException;
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
        Optional<Billing> billingOptional = billingRepository.findById(id);
        if(billingOptional.isPresent()) {
            return BillingDto.builder()
                    .id(billingOptional.get().getId())
                    .idReserva(billingOptional.get().getIdReserva())
                    .idUsuario(billingOptional.get().getIdUsuario())
                    .fechaEmision(billingOptional.get().getFechaEmision())
                    .montoTotal(billingOptional.get().getMontoTotal())
                    .build();
        }else {
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
        List<Billing>listBilling= billingRepository.reporteTotalFacturadoEnFecha(year,startMonth,endMonth);
        Double montoTotal=0.0;
        for(Billing b:listBilling){
            montoTotal+=b.getMontoTotal();
        }
        return TotalFacturadoDto.builder()
                .totalFacturado(montoTotal)
                .year(year)
                .startMonth(startMonth)
                .endMonth(endMonth).build();
    }

    public BillingDto deleteBilling (Long id){
        Optional<Billing> billingDelete = billingRepository.findById(id);
        if(billingDelete.isPresent()){
        billingRepository.deleteById(id);
            return BillingDto.builder()
                    .id(billingDelete.get().getId())
                    .idUsuario(billingDelete.get().getIdUsuario())
                    .idReserva(billingDelete.get().getIdReserva())
                    .fechaEmision(billingDelete.get().getFechaEmision())
                    .montoTotal(billingDelete.get().getMontoTotal()).build();
        }else {
            throw new NotExistsException("El id: " +id+ " no Existe");
        }
    }
}