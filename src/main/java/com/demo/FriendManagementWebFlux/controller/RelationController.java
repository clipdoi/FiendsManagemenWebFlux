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

    @PostMapping("/friends")
    public Mono<RetrieveFriendsListDto.Response> retrieveFriendsList(
             @RequestBody RetrieveFriendsListDto.Request emailRequest) {
//        return Mono.just(RetrieveFriendsListDto.Response.builder()
//                .success(true)
//                .friends((List<String>) relationService.retrieveFriendsList(emailRequest))
//                .count(relationService.retrieveFriendsList(emailRequest)).build());
        Mono<RetrieveFriendsListDto.Response> just = Mono.just(relationService.retrieveFriendsList(emailRequest)
                .flatMap(data ->
                        RetrieveFriendsListDto.Response.builder()
                                .success(true)
                                .friends(data).count(data.size()).build()));
//        return relationService.retrieveFriendsList(emailRequest);
    }

}
