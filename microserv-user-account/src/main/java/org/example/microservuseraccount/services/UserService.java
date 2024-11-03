package org.example.microservuseraccount.services;

import org.example.microservuseraccount.entity.User;
import org.example.microservuseraccount.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;

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


}
