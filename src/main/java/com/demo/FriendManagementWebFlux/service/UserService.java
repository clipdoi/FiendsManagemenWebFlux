package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {
    Mono<List<User>> getAllUsers();
    Mono<User> getUserById(Long id);
    Mono<User> saveUser(User user);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUser(Long id);
}
