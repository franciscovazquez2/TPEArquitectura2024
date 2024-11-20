package org.example.microservuseraccount.services;

import org.example.microservuseraccount.client.ScooterClient;
import org.example.microservuseraccount.client.TravelClient;
import org.example.microservuseraccount.dto.*;
import org.example.microservuseraccount.entity.Account;
import org.example.microservuseraccount.entity.Authority;
import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.error.exception.NotExistsException;
import org.example.microservuseraccount.repository.AuthorityRepository;
import org.example.microservuseraccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ScooterClient scooterClient;

    @Autowired
    private TravelClient travelClient;

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

    public UserDto deleteUser(Long id){
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional.isPresent()) {
            userRepository.deleteById(id);
            return UserDto.builder()
                    .id(userOptional.get().getId())
                    .nombre(userOptional.get().getNombre())
                    .apellido(userOptional.get().getApellido())
                    .email(userOptional.get().getEmail())
                    .telefono(userOptional.get().getTelefono())
                    .accounts(userOptional.get().getAccounts())
                    .rol(userOptional.get().getRoles()).build();
        }else {
            throw new NotExistsException("El id que intentas eliminar no existe" + " ID: " +id);
        }
    }

    //metodo necesario para validar el token(busca las autoridades que le corresponden al usuario)
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


    public Double getsaldo(Long idUser) {
        Optional<User> optionalUser = userRepository.findById(idUser);
        if (!optionalUser.isPresent()) {
            throw new NotExistsException("el usuario no existe");
        }
        List<Account> account = optionalUser.get().getAccounts();
        Double result=0.0;
        for (Account ac: account){
            result+=ac.getSaldo();
        }
        return result;
    }

    public TravelDto iniciarViaje(Long id_user, Long id_scooter) {
    ScooterDTO scooterDTO = scooterClient.findScooterBuyId(id_scooter);
        if(!scooterDTO.isAvailable()){
        throw new NotExistsException("id scooter no disponible"+id_scooter);
    }
        if(this.getsaldo(id_user)<=0.0){
        throw new NotExistsException("saldo insuficiente"+id_user);
    }
    ScooterDTO scooterDTO1=scooterClient.startTrip(id_scooter);
        return travelClient.startTravel(id_user,id_scooter);
    }
}
