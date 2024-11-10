package org.example.microservuseraccount.services;

import jakarta.ws.rs.BadRequestException;
import org.example.microservuseraccount.dto.AccountDto;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.error.exception.RequestBadException;
import org.example.microservuseraccount.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    //lista las cuentas
    public List<AccountDto> getAllAccounts(){
            List<Account> accountList= accountRepository.findAll();
            List<AccountDto> result = new ArrayList<>();
            for(Account account : accountList){
                result.add( AccountDto.builder()
                        .id(account.getId())
                        .cuentaMP(account.getCuentaMP())
                        .fechaAlta(account.getFechaAlta())
                        .saldo(account.getSaldo())
                        .active(account.isActive())
                        .users(account.getUsers()).build());
            }
            return result;
    }

    //cuenta por id
    public AccountDto getAccount(Long id) throws NotExistsException{
            Optional<Account> accountOptional= accountRepository.findById(id);
            if(!accountOptional.isPresent()){
                throw new NotExistsException("No existe la cuenta con el ID: " + id);
            }
            Account account = accountOptional.get();
                return AccountDto.builder().id(account.getId())
                                           .cuentaMP(account.getCuentaMP())
                                           .fechaAlta(account.getFechaAlta())
                                           .saldo(account.getSaldo())
                                           .active(account.isActive())
                                           .users(account.getUsers()).build();
    }

    //crea cuenta
    public AccountDto createAccount(Account newMaintenance){
            Account account = accountRepository.save(newMaintenance);
            return AccountDto.builder()
                             .id(account.getId())
                             .cuentaMP(account.getCuentaMP())
                             .fechaAlta(account.getFechaAlta())
                             .saldo(account.getSaldo())
                             .active(account.isActive())
                             .users(account.getUsers()).build();
    }

    //anula una cuenta
    public AccountDto anularCuenta(Long id) throws RequestBadException {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (!optionalAccount.isPresent()) {
            throw new NotExistsException("La cuenta no existe ID: " + id);
        }
        Account account = optionalAccount.get();
        if (account.isActive()) {
            account.setActive(false);
        }
        Account resultAccount = accountRepository.save(account);
        return AccountDto.builder()
                .id(resultAccount.getId())
                .cuentaMP(resultAccount.getCuentaMP())
                .fechaAlta(resultAccount.getFechaAlta())
                .saldo(resultAccount.getSaldo())
                .active(resultAccount.isActive())
                .users(resultAccount.getUsers()).build();
    }
}
