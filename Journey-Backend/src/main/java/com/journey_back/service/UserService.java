package com.journey_back.service;

import com.journey_back.infra.exception.ValidationError;
import com.journey_back.model.UserModel;
import com.journey_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    // Construtor
    public UserService(UserRepository repository) {
        this.userRepository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    // Listar Usuarios
    public List<UserModel> getUsers() {
        List<UserModel> list = userRepository.findAll();
        return list;
    }

    // Cadastrar Usuario
    public UserModel postUser(UserModel user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            String encoder = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoder);
            UserModel newUser = user;
            userRepository.save(newUser);
            return newUser;
        } else {
            throw new ValidationError("email inserido ja cadastrado");
        }
    }

    // Deletar Usuario
    public boolean deleteUser(Integer id) {
        var user = userRepository.findById(id);
        Integer idUserFind = user.get().getId();
        if(idUserFind != null) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
