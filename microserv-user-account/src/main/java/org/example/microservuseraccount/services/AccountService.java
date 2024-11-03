package org.example.microservuseraccount.services;

import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //lista todas las cuentas
    public List<Account> getAllAccounts(){
        return accountRepository.findAll();
    }

    //devuelve una cuenta por id
    public Optional<Account> getAccount(Long id){
        return accountRepository.findById(id);
    }

    //crea un registro de cuenta
    public Account createAccount(Account newMaintenance){
        return accountRepository.save(newMaintenance);
    }
}
