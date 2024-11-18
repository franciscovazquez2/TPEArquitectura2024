package org.example.microservbilling.services;
import org.example.microservbilling.dto.BillingDto;
import org.example.microservbilling.dto.FeeDto;
import org.example.microservbilling.entity.Billing;
import org.example.microservbilling.entity.Fee;
import org.example.microservbilling.error.exception.NotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.microservbilling.repository.FeeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service ("FeeService")
public class FeeService {
    @Autowired
    private FeeRepository feeRepository;

    //lista todas las tarifas
    public List<FeeDto> getAllFees(){
        List<Fee> feeList = feeRepository.findAll();
        List<FeeDto> result = new ArrayList<>();
        for (Fee fee : feeList) {
            FeeDto feeDto = FeeDto.builder()
                    .id(fee.getId())
                    .monto(fee.getMonto())
                    .fechaInicio(fee.getFechaInicio())
                    .tipo(fee.getTipo()).build();
            result.add(feeDto);
        }
        return result;
    }

    //devuelve una tarifa por id
    public FeeDto getFee(Long id){
        Optional<Fee>feeOptional=feeRepository.findById(id);
        if(feeOptional.isPresent()){
        return FeeDto.builder()
                .id(feeOptional.get().getId())
                .monto(feeOptional.get().getMonto())
                .fechaInicio(feeOptional.get().getFechaInicio())
                .tipo(feeOptional.get().getTipo()).build();
        }else {
        throw new NotExistsException("El id: " + id + " No existe");
        }
    }


    //crea una tarifa
    public FeeDto createFee(Fee newFee){
        Fee fee= feeRepository.save(newFee);
        return FeeDto.builder()
                .id(fee.getId())
                .monto(fee.getMonto())
                .fechaInicio(fee.getFechaInicio())
                .tipo(fee.getTipo()).build();
    }
    //elimina una tarifa
    public FeeDto delteFee(Long id){
        Optional<Fee> feeOptional = feeRepository.findById(id);
        if(feeOptional.isPresent()) {
            feeRepository.deleteById(id);
            return FeeDto.builder().id(feeOptional.get().getId())
                                    .fechaInicio(feeOptional.get().getFechaInicio())
                                    .monto(feeOptional.get().getMonto())
                                    .tipo(feeOptional.get().getTipo()).build();
        }else {
            throw new NotExistsException("El id que quieres eliminar no existe. ID: " +id);
        }
    }
}