package org.example.microservbilling.services;
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

    //lista todos los repositorios
    public List<Billing> getAllBillings(){
        return billingRepository.findAll();
    }

    //devuelve un repositorio por id
    public Optional<Billing> getBilling(Long id){
        return billingRepository.findById(id);
    }

    //crea una factura
    public Billing createBilling(Billing newBilling){
        return billingRepository.save(newBilling);
    }
}