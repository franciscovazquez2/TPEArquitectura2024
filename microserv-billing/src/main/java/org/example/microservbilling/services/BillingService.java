package org.example.microservbilling.services;
import org.example.microservbilling.dto.TotalFacturadoDto;
import org.example.microservbilling.entity.Billing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservbilling.repository.BillingRepository;
import java.util.List;
import java.util.Optional;

@Service ("BillingService")
public class BillingService {
    @Autowired
    private BillingRepository billingRepository;

    //lista de todas las facturas
    public List<Billing> getAllBillings(){
        return billingRepository.findAll();
    }

    //devuelve una factura
    public Optional<Billing> getBilling(Long id){
        return billingRepository.findById(id);
    }

    //crea una factura
    public Billing createBilling(Billing newBilling){
        return billingRepository.save(newBilling);
    }

    public TotalFacturadoDto reporteTotalFacturadoEnFecha(int year, int startMonth,int endMonth){
        return billingRepository.reporteTotalFacturadoEnFecha(year,startMonth,endMonth);
    }
}