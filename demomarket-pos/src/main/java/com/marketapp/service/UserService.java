package com.marketapp.service;

import com.marketapp.model.User;
import com.marketapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Optional<User> login(String username, String password) {
        return repository.findByUsername(username)
                .filter(user -> user.getPassword() != null && user.getPassword().equals(password));
    }

    public User register(User user) {
        return repository.save(user);
    }
}
