package com.journey_back.controller;

import com.journey_back.model.UserModel;
import com.journey_back.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Construtor
    public UserController(UserService service) {
        this.userService = service;
    }


    // Listar Usuarios
    @GetMapping
    public ResponseEntity<List<UserModel>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    // Cadastrar Usuario
    @PostMapping
    public ResponseEntity<UserModel> registerUser(@RequestBody @Valid UserModel userModel) {
        return ResponseEntity.status(201).body(userService.postUser(userModel));
    }

    // Deletar Usuario
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Integer id) {
        var exists = userService.deleteUser(id);
        if (!exists) {
            throw new RuntimeException("Usuário não encontrado");
        } else {
            return ResponseEntity.status(204).build();
        }
    }

}
