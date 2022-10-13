package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

public interface RelationService {

    Mono<User> findByEmail(String email);

    Mono<AddFriendDto.Response> addFriend(AddFriendDto.Request friendRequest);

    Mono<List<String>> getFriendList(RetrieveFriendsListDto.Request emailRequest);

    Mono<List<String>> getCommonFriends(AddFriendDto.Request friendRequest);

    Mono<SubscribeAndBlockDto.Response> subscribeTo(SubscribeAndBlockDto.Request subscribeRequest);

    Mono<SubscribeAndBlockDto.Response> blockEmail(SubscribeAndBlockDto.Request subscribeRequest);

    Mono<Set<String>> retrieveEmails(RetrieveEmailsListReceiveUpdateDto.Request retrieveRequest);

}
