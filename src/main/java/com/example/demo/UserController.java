package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;


    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.ok("Usuario creado exitosamente");
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/borrar-usuarios")
    public ResponseEntity<String> deleteUsers(){
        userRepository.deleteAll();
        return ResponseEntity.ok("Se han borrado los usuarios de la base de datos");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        Optional<User> userOptional = userRepository.findByUsernameAndPassword(username, password);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok("Inicio de sesión exitoso");
        } else {
            return ResponseEntity.badRequest().body("Nombre de usuario o contraseña incorrectos");
        }
    }
}