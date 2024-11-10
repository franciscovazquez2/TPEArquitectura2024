package org.example.microservuseraccount.services;

import org.example.microservuseraccount.dto.AccountDto;
import org.example.microservuseraccount.entity.Account;
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
        try {
            List<Account>accountList= accountRepository.findAll();
            List<AccountDto>result = new ArrayList<>();
            for(Account account : accountList){
                AccountDto accountDto = AccountDto.builder()
                        .id(account.getId())
                        .cuentaMP(account.getCuentaMP())
                        .fechaAlta(account.getFechaAlta())
                        .saldo(account.getSaldo())
                        .active(account.isActive())
                        .users(account.getUsers()).build();
            }
            return result;
        }catch (Exception e){
            throw new NoSuchElementException("error al listar las cuentas");
        }
    }

    //cuenta por id
    public AccountDto getAccount(Long id){
        try {
            Optional<Account> accountOptional= accountRepository.findById(id);
            Account account = accountOptional.get();
            AccountDto accountDto = AccountDto.builder()
                    .id(account.getId())
                    .cuentaMP(account.getCuentaMP())
                    .fechaAlta(account.getFechaAlta())
                    .saldo(account.getSaldo())
                    .active(account.isActive())
                    .users(account.getUsers()).build();
            return accountDto;
        } catch (Exception e) {
            throw new NoSuchElementException("error al buscar cuenta id:"+id);
        }
    }

    //crea cuenta
    public AccountDto createAccount(Account newMaintenance){
        try {
            Account account = accountRepository.save(newMaintenance);
            AccountDto accountDto = AccountDto.builder()
                    .id(account.getId())
                    .cuentaMP(account.getCuentaMP())
                    .fechaAlta(account.getFechaAlta())
                    .saldo(account.getSaldo())
                    .active(account.isActive())
                    .users(account.getUsers()).build();
            return accountDto;
        } catch (Exception e) {
            throw new NoSuchElementException("error al crear la cuenta");
        }
    }

    //anula una cuenta
    public AccountDto anularCuenta(Long id) {
        try {
            Optional<Account> optionalAccount = accountRepository.findById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if (account.isActive()) {
                    account.setActive(false);
                }
                Account resultAccount = accountRepository.save(account);
                AccountDto accountDto = AccountDto.builder()
                        .id(resultAccount.getId())
                        .cuentaMP(resultAccount.getCuentaMP())
                        .fechaAlta(resultAccount.getFechaAlta())
                        .saldo(resultAccount.getSaldo())
                        .active(resultAccount.isActive())
                        .users(resultAccount.getUsers()).build();
                return accountDto;
            }
            throw new NoSuchElementException("cuenta no encontrada");
        } catch (Exception e) {
            throw new NoSuchElementException("error al anular la cuenta id:" + id);
        }
    }
}
