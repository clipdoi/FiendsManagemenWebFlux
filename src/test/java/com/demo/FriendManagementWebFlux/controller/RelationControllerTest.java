package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.service.RelationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest(RelationController.class)
@AutoConfigureMockMvc
public class RelationControllerTest {

    @MockBean
    private RelationService relationService;

    @Autowired
    WebTestClient webTestClient;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void addFriendSuccessTest() {
        AddFriendDto.Request friendRequest = new AddFriendDto.Request();
        friendRequest.setFriends(Arrays.asList("abcxyz@gmail.com", "example@gmail.com"));

        when(relationService.addFriend(any(AddFriendDto.Request.class))).thenReturn(Mono.just(AddFriendDto.Response.builder().success(true).build()));
        webTestClient.post().uri("/api/relation/add")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(friendRequest), AddFriendDto.Request.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true);
    }

    @Test
    public void retrieveFriendsListSuccessTest() {
        RetrieveFriendsListDto.Request emailRequest = new RetrieveFriendsListDto.Request();
        emailRequest.setEmail("hongSon@gmail.com");

        List<String> listEmails = Arrays.asList("abcxyz@gmail.com", "example@gmail.com");
        RetrieveFriendsListDto.Response emailResponse = RetrieveFriendsListDto.Response.builder()
                .friends(listEmails)
                .count(listEmails.size())
                .success(true)
                .build();

        when(relationService.getFriendList(any(RetrieveFriendsListDto.Request.class))).thenReturn(Mono.just(emailResponse));
        webTestClient.post().uri("/api/relation/friends")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(emailRequest), RetrieveFriendsListDto.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.friends[0]").isEqualTo("abcxyz@gmail.com")
                .jsonPath("$.friends[1]").isEqualTo("example@gmail.com")
                .jsonPath("$.count").isEqualTo(listEmails.size());
    }

    @Test
    public void getCommonFriendsSuccessTest() {
        AddFriendDto.Request friendRequest = new AddFriendDto.Request();
        friendRequest.setFriends(Arrays.asList("abcxyz@gmail.com", "example@gmail.com"));

        List<String> listEmails = Arrays.asList("example1@gmail.com", "example2@gmail.com");
        RetrieveFriendsListDto.Response emailResponse = RetrieveFriendsListDto.Response.builder()
                .friends(listEmails)
                .count(listEmails.size())
                .success(true)
                .build();

        when(relationService.getCommonFriends(any(AddFriendDto.Request.class))).thenReturn(Mono.just(emailResponse));
        webTestClient.post().uri("/api/relation/common")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(friendRequest), AddFriendDto.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true)
                .jsonPath("$.friends[0]").isEqualTo("example1@gmail.com")
                .jsonPath("$.friends[1]").isEqualTo("example2@gmail.com")
                .jsonPath("$.count").isEqualTo(listEmails.size());
    }

    @Test
    public void subscribeToSuccessTest() {
        SubscribeAndBlockDto.Request subscribeRequest = new SubscribeAndBlockDto.Request();
        subscribeRequest.setRequester("abcxyz@gmail.com");
        subscribeRequest.setTarget("example@gmail.com");

        when(relationService.subscribeTo(any(SubscribeAndBlockDto.Request.class))).thenReturn(Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build()));
        webTestClient.post().uri("/api/relation/subscribe")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(subscribeRequest), SubscribeAndBlockDto.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true);
    }

    @Test
    public void blockEmailSuccessTest() {
        SubscribeAndBlockDto.Request blockRequest = new SubscribeAndBlockDto.Request();
        blockRequest.setRequester("abcxyz@gmail.com");
        blockRequest.setTarget("example@gmail.com");

        when(relationService.blockEmail(any(SubscribeAndBlockDto.Request.class))).thenReturn(Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build()));
        webTestClient.put().uri("/api/relation/block")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(blockRequest), SubscribeAndBlockDto.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.success").isEqualTo(true);
    }

    @Test
    public void retrieveEmailSuccessTest() {
        RetrieveEmailsListReceiveUpdateDto.Request request = new RetrieveEmailsListReceiveUpdateDto.Request();
        request.setSender("abcxyz@gmail.com");
        request.setText("Hello World! example0@gmail.com");

        Set<String> listEmails = new HashSet<>();
        listEmails.add("example0@gmail.com");
        listEmails.add("example1@gmail.com");
        listEmails.add("example2@gmail.com");
        RetrieveEmailsListReceiveUpdateDto.Response emailResponse = RetrieveEmailsListReceiveUpdateDto.Response.builder()
                .recipients(listEmails)
                .success(true)
                .build();

        when(relationService.retrieveEmails(any(RetrieveEmailsListReceiveUpdateDto.Request.class))).thenReturn(Mono.just(emailResponse));
        webTestClient.post().uri("/api/relation/retrieve")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), RetrieveEmailsListReceiveUpdateDto.Request.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.recipients.size()").isEqualTo(listEmails.size());

    }

}
