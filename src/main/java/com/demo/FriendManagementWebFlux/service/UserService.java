package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAllUsers();
    Mono<User> getUserById(Long id);
    Mono<User> saveUser(User user);
    Mono<User> updateUser(User user);
    Mono<Void> deleteUser(Long id);
}
