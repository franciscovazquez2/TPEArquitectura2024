package org.example.microservuseraccount.services;

import org.example.microservuseraccount.dto.AccountDto;
import org.example.microservuseraccount.dto.UserDto;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    //lista usuarios
    public List<UserDto> getAllUsers(){
        List<User>userList = userRepository.findAll();
        List<UserDto>result = new ArrayList<>();
        for(User user: userList){
            result.add(  UserDto.builder()
                                .id(user.getId())
                                .nombre(user.getNombre())
                                .apellido(user.getApellido())
                                .telefono(user.getTelefono())
                                .accounts(user.getAccounts())
                                .rol(user.getRol())
                                .build());
        }
        return result;
    }

    //usuario por id
    public UserDto getUser(Long id) throws NotExistsException{
        Optional<User> userOptional=userRepository.findById(id);
        if(!userOptional.isPresent()){
            throw new NotExistsException("El usuario no existe ID: " + id);
        }
        User user = userOptional.get();
        return   UserDto.builder()
                        .id(user.getId())
                        .nombre(user.getNombre())
                        .apellido(user.getApellido())
                        .telefono(user.getTelefono())
                        .accounts(user.getAccounts())
                        .rol(user.getRol())
                        .build();
    }

    //crear usuario
    public UserDto createUser(User newUser){
        User user =userRepository.save(newUser);
        return UserDto.builder()
                      .id(user.getId())
                      .nombre(user.getNombre())
                      .apellido(user.getApellido())
                      .telefono(user.getTelefono())
                      .accounts(user.getAccounts())
                      .rol(user.getRol())
                      .build();
    }

    //asociar una cuenta al usuario
    public UserDto asociarCuenta(Long userId,Long accountId) throws NotExistsException{
        //verifica que existan el usuario y la cuenta
        AccountDto account = accountService.getAccount(accountId);
        Optional <User> optUser = userRepository.findById(userId);
        if((account != null) && (optUser.isPresent())){
            User user = optUser.get();
            //se agrega la cuenta en la lista del usuario
            user.addAccount(new Account(account.getId(),account.getCuentaMP(),account.getFechaAlta(),account.getSaldo(),account.isActive(),account.getUsers()));
            User userResponse = userRepository.save(user);
            return new UserDto(userResponse.getId(),userResponse.getNombre(),userResponse.getApellido(),userResponse.getEmail(),userResponse.getTelefono(),userResponse.getAccounts(),userResponse.getRol());
        }
        else if(!optUser.isPresent()){
            throw new NotExistsException("Usuario no encontrado. ID: " + userId);
        }else{
            throw new NotExistsException("Cuenta no encontrada. ID: " + accountId);
        }
    }

}
