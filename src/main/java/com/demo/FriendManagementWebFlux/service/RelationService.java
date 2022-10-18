package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.model.User;
import reactor.core.publisher.Mono;

public interface RelationService {

    Mono<User> findByEmail(String email);

    Mono<AddFriendDto.Response> addFriend(AddFriendDto.Request friendRequest);

    Mono<RetrieveFriendsListDto.Response> getFriendList(RetrieveFriendsListDto.Request emailRequest);

    Mono<RetrieveFriendsListDto.Response> getCommonFriends(AddFriendDto.Request friendRequest);

    Mono<SubscribeAndBlockDto.Response> subscribeTo(SubscribeAndBlockDto.Request subscribeRequest);

    Mono<SubscribeAndBlockDto.Response> blockEmail(SubscribeAndBlockDto.Request subscribeRequest);

    Mono<RetrieveEmailsListReceiveUpdateDto.Response> retrieveEmails(RetrieveEmailsListReceiveUpdateDto.Request retrieveRequest);

}
