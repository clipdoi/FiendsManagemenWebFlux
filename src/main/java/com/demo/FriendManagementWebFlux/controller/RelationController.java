package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import com.demo.FriendManagementWebFlux.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/relation")
public class RelationController {

    @Autowired
    private RelationService relationService;

    @Autowired
    private FriendRelationshipRepository repository;

    @Autowired
    private UserRepository userRepository;

    //create a friend connection between two email addresses
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<AddFriendDto.Response> addFriend(@RequestBody AddFriendDto.Request friendRequest) {
        return relationService.addFriend(friendRequest);
    }

    //retrieve friends list for an email address
    @PostMapping("/friends")
    @ResponseStatus(HttpStatus.OK)
    public Mono<RetrieveFriendsListDto.Response> retrieveFriendsList(
             @RequestBody RetrieveFriendsListDto.Request emailRequest) {
        return Mono.just(RetrieveFriendsListDto.Response.builder()
                .success(true)
                .friends(relationService.retrieveFriendsList(emailRequest).toProcessor().block())
                .count(relationService.retrieveFriendsList(emailRequest).toProcessor().block().size()).build());
    }

    //retrieve the common friends list between two email addresses
    @PostMapping("/common")
    public Mono<RetrieveFriendsListDto.Response> getCommonFriends(
            @RequestBody AddFriendDto.Request friendRequest) {
        return Mono.just(RetrieveFriendsListDto.Response.builder()
                .success(true)
                .friends(relationService.getCommonFriends(friendRequest).toProcessor().block())
                .count(relationService.getCommonFriends(friendRequest).toProcessor().block().size()).build());
    }

}
