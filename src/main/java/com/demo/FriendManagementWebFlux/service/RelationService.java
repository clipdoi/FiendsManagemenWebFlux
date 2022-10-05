package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RelationService {

    Mono<User> findByEmail(String email);

    Mono<AddFriendDto.Response> addFriend(AddFriendDto.Request friendRequest);

    Mono<List<String>> retrieveFriendsList(RetrieveFriendsListDto.Request emailRequest);

}
