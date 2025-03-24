package com.webserver.Payroll.controllers;

import java.util.List;

import com.webserver.Payroll.entities.User;
import com.webserver.Payroll.exceptions.UserNotFoundException;
import com.webserver.Payroll.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository userRepository) {
        this.repository = userRepository;
    }

    // Get all the users
    @GetMapping("/users")
    List<User> getAllUsers() {
        return repository.findAll();
    }

    // Create a new user
    @PostMapping("/users")
    User createUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Get a single user
    @GetMapping("/users/{id}")
    User getUser(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    // Update a user
    @PutMapping("/users/{id}")
    User updateUser( @RequestBody User newUser, @PathVariable Long id) {
        return repository.findById(id)
                // If we find the user with this id, we will update this user with our new user
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setBalance(newUser.getBalance());
                    return repository.save(user);
                })
                // If we don't find the user with this id, we create the new user
                .orElseGet(() -> repository.save(newUser));
    }

    // Delete a user
    @DeleteMapping("/users{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
