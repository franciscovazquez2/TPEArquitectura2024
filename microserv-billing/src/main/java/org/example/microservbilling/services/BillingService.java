package org.example.microservbilling.services;
import org.example.microservbilling.dto.BillingDTO;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.example.microservbilling.error.exception.NotExistsException;
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
    public List<BillingDTO> getAllBillings(){
        List<Billing> listBilling = billingRepository.findAll();
        List<BillingDTO> result = new ArrayList<>();
        listBilling.forEach(billing -> result.add(BillingDTO.builder()
                                                            .id(billing.getId())
                                                            .fechaEmision(billing.getFechaEmision())
                                                            .idReserva(billing.getIdReserva())
                                                            .idUsuario(billing.getIdUsuario())
                                                            .montoTotal(billing.getMontoTotal())
                                                            .build()));

        return result;

    }

    //devuelve una factura
    public BillingDTO getBilling(Long id){
        Optional<Billing> billing = billingRepository.findById(id);
        if(billing.isPresent()){
            return BillingDTO.builder().id(billing.get().getId())
                    .fechaEmision(billing.get().getFechaEmision())
                    .idReserva(billing.get().getIdReserva())
                    .idUsuario(billing.get().getIdUsuario())
                    .montoTotal(billing.get().getMontoTotal())
                    .build();
        }else{
            throw new NotExistsException("El id: " + id + "No existe");
        }
    }

    //crea una factura
    public Billing createBilling(Billing newBilling){
        return billingRepository.save(newBilling);
    }

    public TotalFacturadoDto reporteTotalFacturadoEnFecha(int year, int startMonth,int endMonth){
        return billingRepository.reporteTotalFacturadoEnFecha(year,startMonth,endMonth);
    }
}