package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.exception.InputInvalidException;
import com.demo.FriendManagementWebFlux.exception.StatusException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import com.demo.FriendManagementWebFlux.utils.RequestValidation;
import com.demo.FriendManagementWebFlux.utils.constraints.ErrorConstraints;
import com.demo.FriendManagementWebFlux.utils.constraints.FriendStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("relationService")
@Slf4j
public class RelationServiceImpl implements RelationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FriendRelationshipRepository relationshipRepository;

    @Override
    public Mono<User> findByEmail(String email) {
        return Mono.just(userRepository.findByEmail(email)).filter(Optional::isPresent).map(Optional::get)
                .switchIfEmpty(Mono.error(new DataNotFoundException(ErrorConstraints.EMAIL_NOT_FOUND)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<AddFriendDto.Response> addFriend(@Valid AddFriendDto.Request friendRequest) {
//        String error = RequestValidation.checkAddAndGetCommonRequest(friendRequest);
//        if (!error.equals("")) {
//            throw new InputInvalidException(error);
//        }

        return Mono.zip(Mono.just(userRepository.findByEmail(friendRequest.getFriends().get(0))).filter(Optional::isPresent).map(Optional::get).map(User::getId),
                Mono.just(userRepository.findByEmail(friendRequest.getFriends().get(1))).filter(Optional::isPresent).map(Optional::get).map(User::getId))
                .flatMap(data -> {
                    log.info("data :{} :: {}", data.getT1(), data.getT2());
                     Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                                .filter(Optional::isPresent).map(Optional::get)
                                .filter(a -> !a.getStatus().contains(FriendStatusEnum.FRIEND.name()))
                                .switchIfEmpty(Mono.error(new StatusException("Two Email have already being friend")))
                                .filter(a -> !a.getStatus().contains(FriendStatusEnum.BLOCK.name()))
                                .switchIfEmpty(Mono.error(new StatusException("This email has been blocked !!")))
                                .subscribe();
                     return Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(relationship -> !relationship.isPresent())
                            .flatMap(relationship -> {
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT1())
                                        .friendId(data.getT2())
                                        .status(FriendStatusEnum.FRIEND.name()).build());
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT2())
                                        .friendId(data.getT1())
                                        .status(FriendStatusEnum.FRIEND.name()).build());
                                return Mono.just(AddFriendDto.Response.builder().success(true).build());
                            }).switchIfEmpty(Mono.just(AddFriendDto.Response.builder().success(false).build()));
                        }
                );
    }

    @Override
    public Mono<List<String>> retrieveFriendsList(RetrieveFriendsListDto.Request emailRequest) {
            return findByEmail(emailRequest.getEmail())
                    .map(user -> userRepository.getListFriendEmails(user.getId()));
    }

    @Override
    public Mono<List<String>> getCommonFriends(AddFriendDto.Request friendRequest) {
        return Mono.zip(Mono.just(userRepository.findByEmail(friendRequest.getFriends().get(0))).filter(Optional::isPresent).map(Optional::get).map(User::getId),
                Mono.just(userRepository.findByEmail(friendRequest.getFriends().get(1))).filter(Optional::isPresent).map(Optional::get).map(User::getId))
                .map(data -> userRepository.getCommonFriends(data.getT1(), data.getT2()));
    }

    @Override
    public Mono<SubscribeAndBlockDto.Response> subscribeTo(SubscribeAndBlockDto.Request subscribeRequest) {
        return Mono.zip(Mono.just(userRepository.findByEmail(subscribeRequest.getRequester())).filter(Optional::isPresent).map(Optional::get).map(User::getId),
                Mono.just(userRepository.findByEmail(subscribeRequest.getTarget())).filter(Optional::isPresent).map(Optional::get).map(User::getId))
                .flatMap(data -> {
                    log.info("data :{} :: {}", data.getT1(), data.getT2());
                    Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(Optional::isPresent).map(Optional::get)
                            .filter(a -> !a.getStatus().contains(FriendStatusEnum.FRIEND.name()))
                            .switchIfEmpty(Mono.error(new StatusException("Already being friend of this target, no need to subscribe !")))
                            .filter(a -> !a.getStatus().contains(FriendStatusEnum.BLOCK.name()))
                            .switchIfEmpty(Mono.error(new StatusException("Target email has been blocked !")))
                            .filter(a -> !a.getStatus().contains(FriendStatusEnum.SUBSCRIBE.name()))
                            .switchIfEmpty(Mono.error(new StatusException("Already subscribed to this target email !")))
                            .subscribe();
                    return Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(relationship -> !relationship.isPresent())
                            .flatMap(relationship -> {
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT1())
                                        .friendId(data.getT2())
                                        .status(FriendStatusEnum.SUBSCRIBE.name()).build());
                                return Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build());
                            }).switchIfEmpty(Mono.just(SubscribeAndBlockDto.Response.builder().success(false).build()));
                });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Mono<SubscribeAndBlockDto.Response> blockEmail(SubscribeAndBlockDto.Request subscribeRequest) {
        return Mono.zip(Mono.just(userRepository.findByEmail(subscribeRequest.getRequester())).filter(Optional::isPresent).map(Optional::get).map(User::getId),
                        Mono.just(userRepository.findByEmail(subscribeRequest.getTarget())).filter(Optional::isPresent).map(Optional::get).map(User::getId))
                .flatMap(data -> {
                    log.info("data :{} :: {}", data.getT1(), data.getT2());

                     Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                             .filter(Optional::isPresent).map(Optional::get)
                             .filter(a -> a.getStatus().contains(FriendStatusEnum.FRIEND.name()))
                             .log("a")
                            .flatMap(relationship -> {
                                relationshipRepository.updateStatusByEmailIdAndFriendId(data.getT1(), data.getT2());
                                return Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build());
                            }).switchIfEmpty(Mono.just(SubscribeAndBlockDto.Response.builder().success(false).build()))
                             .subscribe();

                    Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(Optional::isPresent).map(Optional::get)
                            .filter(a -> !a.getStatus().contains(FriendStatusEnum.BLOCK.name()))
                            .log("b")
                            .switchIfEmpty(Mono.error(new StatusException("Target email has been blocked !")))
                            .subscribe();

                    Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(Optional::isPresent).map(Optional::get)
                            .filter(a -> a.getStatus().contains(FriendStatusEnum.SUBSCRIBE.name()))
                            .log("c")
                            .flatMap(relationship -> {
                                relationshipRepository.updateStatusByEmailIdAndFriendId(data.getT1(), data.getT2());
                                return Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build());
                            }).switchIfEmpty(Mono.just(SubscribeAndBlockDto.Response.builder().success(false).build()))
                            .subscribe();

                    return Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(relationship -> !relationship.isPresent())
                            .flatMap(relationship -> {
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT1())
                                        .friendId(data.getT2())
                                        .status(FriendStatusEnum.BLOCK.name()).build());
                                return Mono.just(SubscribeAndBlockDto.Response.builder().success(true).build());
                            }).switchIfEmpty(Mono.just(SubscribeAndBlockDto.Response.builder().success(false).build()));
                });
    }
}
