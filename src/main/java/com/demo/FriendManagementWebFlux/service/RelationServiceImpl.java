package com.demo.FriendManagementWebFlux.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("relationService")
@Slf4j
public class RelationServiceImpl implements RelationService {

//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    FriendRelationshipRepository relationshipRepository;

//    @Override
//    public Mono<User> findByEmail(String email) {
//        return Mono.just(userRepository.findByEmail(email).orElseThrow(() -> new DataNotFoundException(ErrorConstraints.EMAIL_NOT_FOUND)));
//    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Mono<Boolean> addFriend(@Valid AddFriendDto.Request friendRequest) {
////        String error = RequestValidation.checkAddAndGetCommonRequest(friendRequest);
////        if (!error.equals("")) {
////            throw new InputInvalidException(error);
////        }
//        return Mono.zip(findByEmail(friendRequest.getFriends().get(0)).map(Optional::get),//map(Email::getEmailId
//                findByEmail(friendRequest.getFriends().get(1)).map(Optional::get)).flatMap(data -> {//map(Email::getEmailId)
//                    relationshipRepository.findByEmailIdAndFriendId(data.getT1().getEmailId(), data.getT2().getEmailId())
//                            .filter(relationship -> relationship.isPresent())
//                            .map(Optional::get)
//                            //.switchIfEmpty(Mono.error(new DataNotFoundException("Relationship not exist")))
//                            .filter(e -> !e.getStatus().contains(FriendStatusEnum.FRIEND.name()))
//                            .switchIfEmpty(Mono.error(new StatusException("Two Email have already being friend")))
//                            .filter(e -> !e.getStatus().contains(FriendStatusEnum.BLOCK.name()))
//                            .switchIfEmpty(Mono.error(new StatusException("This email has been blocked !!")))
//                            .map(relationship -> relationshipRepository.save(Relationship.builder()
//                                    .emailId(data.getT1().getEmailId()).friendId(data.getT2().getEmailId()).status(FriendStatusEnum.FRIEND.name()).build()))
//                            .map(inverseRela -> relationshipRepository.save(Relationship.builder()
//                                    .emailId(data.getT2().getEmailId()).friendId(data.getT1().getEmailId()).status(FriendStatusEnum.FRIEND.name()).build()));
//            return Mono.just(Boolean.TRUE);
//        }).onErrorReturn(Boolean.FALSE);
//    }
}
