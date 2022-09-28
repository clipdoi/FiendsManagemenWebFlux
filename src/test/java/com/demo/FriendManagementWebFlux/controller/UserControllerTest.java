package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.when;

@WebFluxTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    void testFindAll() {
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        User user2 = User.builder().id(1L).email("test2@gmail.com").build();

        when(userService.getAllUsers()).thenReturn(Flux.just(user1, user2));

        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(User.class);

        Mockito.verify(userService, Mockito.times(1)).getAllUsers();
    }

    @Test
    void testFindOneUser() {
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();

        when(userService.getUserById(user1.getId())).thenReturn(Mono.just(user1));

        webTestClient.get()
                .uri("/api/users/{id}", user1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(User.class);

        Mockito.verify(userService, Mockito.times(1)).getUserById(user1.getId());
    }

    @Test
    void testCreateUser() {
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        // when
        when(userService.saveUser(user1)).thenReturn(Mono.just(user1));
        // verifier
        webTestClient.post().uri("/api/users").contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user1), User.class)
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("test1@gmail.com");

    }

    @Test
    void testUpdateUser() {
        // init
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();
        User user2 = User.builder().id(1L).email("test2@gmail.com").build();
        // when
        when(userService.updateUser(user1)).thenReturn(Mono.just(user2));
        // verifier
        webTestClient.put().uri("/api/users").contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(user1), User.class)
                .exchange().expectStatus().isOk()
                .expectBody()
                .jsonPath("$.email").isEqualTo("test2@gmail.com");

    }

    @Test
    void testDeleteOneUser() {
        User user1 = User.builder().id(1L).email("test1@gmail.com").build();

        when(userService.deleteUser(user1.getId())).thenReturn(Mono.empty());
        System.out.println(userService.getUserById(user1.getId()));

        webTestClient.delete()
                .uri("/api/users/{id}", user1.getId())
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(userService, Mockito.times(1)).deleteUser(user1.getId());

    }
}
