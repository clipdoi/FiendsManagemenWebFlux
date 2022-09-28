package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Flux<User> findAll() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("")
    public Mono<User> createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("")
    public Mono<User> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

}
