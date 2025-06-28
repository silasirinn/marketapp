package com.marketapp.controller;

import com.marketapp.model.LoginRequest;
import com.marketapp.model.User;
import com.marketapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        if (userService.login(request.getUsername(), request.getPassword()).isPresent()) {
            return ResponseEntity.ok("Giriş başarılı");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Hatalı kullanıcı adı veya şifre");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        return ResponseEntity.ok(userService.register(user));
    }
}
