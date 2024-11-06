package org.example.microservuseraccount.services;

import org.example.microservuseraccount.dto.UserDto;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.repository.AccountRepository;
import org.example.microservuseraccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AccountService accountService;

    //lista todos los usuarios
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    //devuelve un usuario por id
    public Optional<User> getUser(Long id){
        return userRepository.findById(id);
    }

    //crea un registro de usuario
    public User createUser(User newUser){
        return userRepository.save(newUser);
    }

    //asociar una cuenta al usuario
    public UserDto asociarCuenta(Long userId,Long accountId){
        //verifica que existan el usuario y la cuenta
        Optional<Account> optAccount = accountService.getAccount(accountId);
        Optional <User> optUser = userRepository.findById(userId);

        if(optAccount.isPresent()&&optUser.isPresent()){
            User user = optUser.get();
            Account account = optAccount.get();
            //se agrega la cuenta en la lista del usuario
            user.addAcount(account);
            User userResponse = userRepository.save(user);
            return new UserDto(userResponse.getId(),userResponse.getNombre(),userResponse.getApellido(),userResponse.getEmail(),userResponse.getTelefono(),userResponse.getAcounts(),userResponse.getRol());
        }
        else {
            throw new NoSuchElementException("cuenta/usuario inexistente");
        }
    }

}
