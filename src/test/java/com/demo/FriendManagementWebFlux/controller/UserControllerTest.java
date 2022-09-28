package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private List<User> users;

    @BeforeAll
    public void setUp() {

    }

    @AfterAll
    public void clear() {
        users = new ArrayList<>();
    }

    @Test
    public void shouldFindAll() {
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).email("abc1@gmail.com").build());
        users.add(User.builder().id(2L).email("abc2@gmail.com").build());
        users.add(User.builder().id(3L).email("abc3@gmail.com").build());
        users.add(User.builder().id(4L).email("abc4@gmail.com").build());
        when(userService.getAllUsers()).thenReturn(Mono.just(users));
//
//        webTestClient
//                .get().uri("/users")
//                .exchange()
//                .expectStatus()
//                .isOk()
//                .expectBody(User.class);
    }
}
