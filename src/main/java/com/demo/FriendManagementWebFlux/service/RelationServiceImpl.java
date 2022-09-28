package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.exception.StatusException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import com.demo.FriendManagementWebFlux.utils.constraints.ErrorConstraints;
import com.demo.FriendManagementWebFlux.utils.constraints.FriendStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
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
    public Mono<Boolean> addFriend(@Valid AddFriendDto.Request friendRequest) {
//        String error = RequestValidation.checkAddAndGetCommonRequest(friendRequest);
//        if (!error.equals("")) {
//            throw new InputInvalidException(error);
//        }
        return Mono.zip(findByEmail(friendRequest.getFriends().get(0)).map(User::getId),
                        findByEmail(friendRequest.getFriends().get(1)).map(User::getId))
                .flatMap(data -> {
                    return Mono.just(relationshipRepository.findByEmailIdAndFriendId(data.getT1(), data.getT2()))
                            .filter(Optional::isPresent)
                            .map(Optional::get)
                            .filter(e -> !e.getStatus().contains(FriendStatusEnum.FRIEND.name()))
                            .switchIfEmpty(Mono.error(new StatusException("Two Email have already being friend")))
                            .filter(e -> !e.getStatus().contains(FriendStatusEnum.BLOCK.name()))
                            .switchIfEmpty(Mono.error(new StatusException("This email has been blocked !!")))
                            .doOnNext(relationship -> {
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT1())
                                        .friendId(data.getT2())
                                        .status(FriendStatusEnum.FRIEND.name()).build());
                                relationshipRepository.save(UserRelationship.builder()
                                        .emailId(data.getT2())
                                        .friendId(data.getT1())
                                        .status(FriendStatusEnum.FRIEND.name()).build());
//                                return Mono.just(Boolean.TRUE);
                            }).thenReturn(true);
                        }
                );
        //return Mono.just(Boolean.FALSE);
    }
}
