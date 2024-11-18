package org.example.microservuseraccount.services;

import org.example.microservuseraccount.dto.*;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.entity.Authority;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.repository.AuthorityRepository;
import org.example.microservuseraccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private AccountService accountService;


    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

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
                                .rol(user.getRoles())
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
                        .rol(user.getRoles())
                        .build();
    }


    //crear usuario
    public UserDto createUser(UserCreateDTO newUser) {

        Optional<Authority> authorityOptional = authorityRepository.findById(newUser.getRol().getName());
        if (authorityOptional.isPresent()) {
            User userCod = new User(newUser.getNombre(), newUser.getApellido(),
                                    newUser.getEmail(), newUser.getTelefono(), newUser.getUser(),
                                    passwordEncoder.encode(newUser.getPassword()));
            userCod.addRol(authorityOptional.get());
            User user = userRepository.save(userCod);
            return UserDto.builder()
                    .id(user.getId())
                    .nombre(user.getNombre())
                    .apellido(user.getApellido())
                    .telefono(user.getTelefono())
                    .accounts(user.getAccounts())
                    .rol(user.getRoles())
                    .build();
        }else {
            throw new NotExistsException("El rol no existe. ROL: " + newUser.getRol().getName());
        }
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
            return new UserDto(userResponse.getId(),userResponse.getNombre(),userResponse.getApellido(),userResponse.getEmail(),userResponse.getTelefono(),userResponse.getAccounts(),userResponse.getRoles());
        }
        else if(!optUser.isPresent()){
            throw new NotExistsException("Usuario no encontrado. ID: " + userId);
        }else{
            throw new NotExistsException("Cuenta no encontrada. ID: " + accountId);
        }
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


    public UserTokenDto findOneWithAuthoritiesByUsernameIgnoreCase(String username ){

        Optional<User> userOptional = userRepository.findOneWithAuthoritiesByUsernameIgnoreCase(username);
        if(!userOptional.isPresent()){
            throw new NotExistsException("No existe el usuario: " + username);
        }
        List<AuthorityDto> authorityDtos = new ArrayList<>();

        if(userOptional.isPresent()){
            userOptional.get().getRoles().forEach(rol ->authorityDtos.add(AuthorityDto.builder().name(rol.getName()).build()));
            return UserTokenDto.builder()
                    .id(userOptional.get().getId())
                    .user(userOptional.get().getUser())
                    .password(userOptional.get().getPassword())
                    .roles(authorityDtos).build();
        }else {
            throw new NotExistsException("El usuario no existe");
        }
    }

}
