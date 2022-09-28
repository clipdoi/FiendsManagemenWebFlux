package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Mono;

public interface RelationService {

    Mono<User> findByEmail(String email);

    Mono<Boolean> addFriend(AddFriendDto.Request friendRequest);

}
