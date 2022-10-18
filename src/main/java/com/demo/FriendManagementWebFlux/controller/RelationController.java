package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import com.demo.FriendManagementWebFlux.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/relation")
public class RelationController {

    @Autowired
    private RelationService relationService;

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
        return relationService.getFriendList(emailRequest);
    }

    //retrieve the common friends list between two email addresses
    @PostMapping("/common")
    @ResponseStatus(HttpStatus.OK)
    public Mono<RetrieveFriendsListDto.Response> getCommonFriends(
            @RequestBody AddFriendDto.Request friendRequest) {
        return relationService.getCommonFriends(friendRequest);
    }

    //to subscribe to update from an email address
    @PostMapping("/subscribe")
    public Mono<SubscribeAndBlockDto.Response> subscribeTo(
            @RequestBody SubscribeAndBlockDto.Request subscribeRequest) {
        return relationService.subscribeTo(subscribeRequest);
    }

    //to block updates from an email address
    @PutMapping("/block")
    public Mono<SubscribeAndBlockDto.Response> blockEmail(
            @RequestBody SubscribeAndBlockDto.Request blockRequest) {
        return relationService.blockEmail(blockRequest);

    }

    //to retrieve all email addresses that can receive updates from an email address
    @PostMapping("/retrieve")
    public Mono<RetrieveEmailsListReceiveUpdateDto.Response> retrieveEmail(
             @RequestBody RetrieveEmailsListReceiveUpdateDto.Request retrieveRequest) {
        return relationService.retrieveEmails(retrieveRequest);
    }
}
