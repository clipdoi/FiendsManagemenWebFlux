package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl service;

    @Mock
    private UserRepository repository;

    @Test
    void testGetAllUsers_Success() {
        List<User> users = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            users.add(new User(1L, "abc@gmail.com"));
        }

        Mockito.when(repository.findAll()).thenReturn(users);

        Flux<User> users2 = service.getAllUsers();

        assertThat(users2.count().block()).isEqualTo(users.size());

        Mockito.verify(repository).findAll();
    }

    @Test
    void testSaveUser_Success(){
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        // logic
        Mockito.when(repository.save(any())).thenReturn(user1);
        // verifier
        StepVerifier.create(service.saveUser(user1))
                .consumeNextWith(data -> {
                    Assertions.assertEquals(data.getEmail(), "test1@gmail.com");
                }).verifyComplete();
    }

    @Test
    void testSaveUser_Fail(){
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        // logic
        Mockito.when(repository.save(any())).thenReturn(Optional.ofNullable(null));
        // verifier
        assertThatThrownBy(() -> service.saveUser(user1))
                .isInstanceOf(Exception.class);
        Mockito.verify(repository).save(any(User.class));
    }

    @Test
    void testGetUserById_Success() {
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();

        Mockito.when(repository.findById(any())).thenReturn(Optional.ofNullable(user1));

        StepVerifier.create(service.getUserById(user1.getId()))
                .consumeNextWith(data -> {
                    Assertions.assertEquals(data.getEmail(), "test1@gmail.com");
                }).verifyComplete();

        Mockito.verify(repository).findById(any(Long.class));
    }

    @Test
    void testGetUserById_NotFound() {
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();

        Mockito.when(repository.findById(any())).thenReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> service.getUserById(user1.getId()))
                .isInstanceOf(DataNotFoundException.class);

        Mockito.verify(repository).findById(any(Long.class));
    }

    @Test
    void testUpdateUser_Success(){
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        User user2 = User.builder().id(1L).email("test2@gmail.com").build();
        // logic
        Mockito.when(repository.findById(any())).thenReturn(Optional.ofNullable(user1));
        Mockito.when(repository.save(any())).thenReturn(user2);
        // verifier
        StepVerifier.create(service.updateUser(user1))
                .consumeNextWith(data -> {
                    Assertions.assertEquals(data.getEmail(), "test2@gmail.com");
                }).verifyComplete();
    }

    @Test
    void testDeleteUser_Success(){
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        // logic
        service.deleteUser(user1.getId());
        // then verify
        Mockito.verify(repository).deleteById(user1.getId());
    }

}
