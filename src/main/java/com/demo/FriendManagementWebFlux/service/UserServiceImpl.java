package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Mono<List<User>> getAllUsers() {
        return Mono.just(userRepository.findAll());
    }

    @Override
    public Mono<User> getUserById(Long id) {
        return Mono.justOrEmpty(userRepository.findById((id)).orElseThrow(() -> new DataNotFoundException("Not found user of id " + id)));
    }

    @Override
    public Mono<User> saveUser(User user) {
        return Mono.justOrEmpty(userRepository.save(user)).switchIfEmpty(Mono.error(new Exception("Fail save user")));
    }

    @Override
    public Mono<User> updateUser(User user) {
        return Mono.just(userRepository.findById(user.getId())
                .map(olderUser -> {
                    if(user.getEmail() != null) olderUser.setEmail(user.getEmail());
                    userRepository.save(olderUser);
                    return olderUser;
                }))
                .flatMap(Mono::justOrEmpty);

    }

    @Override
    public Mono<Void> deleteUser(Long id) {
        userRepository.deleteById(id);
        return Mono.empty();
    }
}
