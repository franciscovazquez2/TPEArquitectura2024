package org.example.microservbilling.services;
import org.example.microservbilling.entity.Fee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservbilling.repository.FeeRepository;
import java.util.List;
import java.util.Optional;

@Service ("FeeService")
public class FeeService {
    @Autowired
    private FeeRepository feeRepository;

    //lista todas las tarifas
    public List<Fee> getAllFees(){
        return feeRepository.findAll();
    }

    //devuelve una tarifa por id
    public Optional<Fee> getFee(Long id){
        return feeRepository.findById(id);
    }

    //crea una tarifa
    public Fee createFee(Fee newFee){
        return feeRepository.save(newFee);
    }
}