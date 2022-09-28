package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Flux<User> getAllUsers() {
        return Flux.fromIterable(userRepository.findAll());
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return Mono.just(userRepository.findById((id)).orElseThrow(() -> new DataNotFoundException("Not found user of id " + id)));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.just(userRepository.save(user)).switchIfEmpty(Mono.error(new Exception("Fail save user")));
    }

    @Override
    public Mono<User> updateUser(User user) {
        return Mono.just(userRepository.findById(user.getId()))
                .filter(Optional::isPresent)
                .map(a -> a.get())
                .doOnNext(entity -> entity.setEmail(user.getEmail()))
                .map(updated -> userRepository.save(updated));
    }
    @Override
    public Mono<Void> deleteUser(Long id) {
        userRepository.deleteById(id);
        return Mono.empty();
    }
}
