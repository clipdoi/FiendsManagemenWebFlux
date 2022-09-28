package com.demo.FriendManagementWebFlux.controller;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.model.User;
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
    @PostMapping(value = "/add")
    public Mono<Boolean> addFriend(@RequestBody AddFriendDto.Request friendRequest) {
        return relationService.addFriend(friendRequest);
    }

    @GetMapping(value = "/getEmail")
    public Mono<User> findByEmail(@RequestParam String email) {
        return relationService.findByEmail(email);
    }
}
