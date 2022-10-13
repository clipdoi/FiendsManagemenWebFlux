package com.demo.FriendManagementWebFlux.service;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveFriendsListDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.exception.DataNotFoundException;
import com.demo.FriendManagementWebFlux.exception.StatusException;
import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import com.demo.FriendManagementWebFlux.utils.constraints.ErrorConstraints;
import com.demo.FriendManagementWebFlux.utils.constraints.FriendStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RelationServiceImplTest {

    @InjectMocks
    private RelationServiceImpl relationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendRelationshipRepository relationship;

    private RetrieveFriendsListDto.Request emailRequest;
    private AddFriendDto.Request addCommonRequest;
    private SubscribeAndBlockDto.Request subBlockRequest;
    private RetrieveEmailsListReceiveUpdateDto.Request retrieveRequest;

    private List<String> listEmail;

    private User emailTest1;
    private User emailTest2;
    private User emailTest3;
    private User emailTest4;
    private User emailTest5;
    private User emailTest6;

    private UserRelationship friendRelationship1;
    private UserRelationship blockRelationship1;
    private UserRelationship subscribeRelationship1;

    private DataNotFoundException dataNotFoundException;
    private StatusException statusException;

    @BeforeAll
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        emailRequest = new RetrieveFriendsListDto.Request();
        addCommonRequest = new AddFriendDto.Request();
        subBlockRequest = new SubscribeAndBlockDto.Request();
        retrieveRequest = new RetrieveEmailsListReceiveUpdateDto.Request();

        emailTest1 = User.builder().id(1L).email("hongson1@gmail.com").build();
        emailTest2 = User.builder().id(2L).email("minhthong1@gmail.com").build();
        emailTest3 = User.builder().id(3L).email("saomai1@gmail.com").build();
        emailTest4 = User.builder().id(6L).email("ngoctu1@gmail.com").build();
        emailTest5 = User.builder().id(4L).email("nguyenquang1@gmail.com").build();
        emailTest6 = User.builder().id(5L).email("kienca1@gmail.com").build();

        friendRelationship1 = UserRelationship.builder().emailId(1L).friendId(2L).status(FriendStatusEnum.FRIEND.name()).build();
        blockRelationship1 = UserRelationship.builder().emailId(5L).friendId(4L).status(FriendStatusEnum.BLOCK.name()).build();
        subscribeRelationship1 = UserRelationship.builder().emailId(6L).friendId(5L).status(FriendStatusEnum.SUBSCRIBE.name()).build();

        listEmail = new ArrayList<>();

        dataNotFoundException = new DataNotFoundException(ErrorConstraints.EMAIL_NOT_FOUND);
    }

    @Test
    public void testAddFriendSuccess() {
        addCommonRequest = new AddFriendDto.Request
                (Arrays.asList(emailTest1.getEmail(), emailTest2.getEmail()));

        when(userRepository.findByEmail(emailTest1.getEmail()))
                .thenReturn(Optional.of(emailTest1));
        when(userRepository.findByEmail(emailTest2.getEmail()))
                .thenReturn(Optional.of(emailTest2));
        when(relationship.findByEmailIdAndFriendId
                (emailTest1.getId(), emailTest2.getId()))
                .thenReturn(Optional.empty());

        StepVerifier.create(relationService.addFriend(addCommonRequest))
                .consumeNextWith(data -> {
                    assertEquals(true, data.getSuccess());
                }).verifyComplete();
    }

//    @Test
//    public void testAddFriendBlockedEmail() {
//        statusException = new StatusException("This email has been blocked !!");
//        addCommonRequest = new AddFriendDto.Request(Arrays.asList(emailTest1.getEmail(), emailTest2.getEmail()));
//
//        when(userRepository.findByEmail(emailTest1.getEmail()))
//                .thenReturn(Optional.of(emailTest1));
//        when(userRepository.findByEmail(emailTest2.getEmail()))
//                .thenReturn(Optional.of(emailTest2));
//        when(relationship.findByEmailIdAndFriendId
//                (emailTest1.getId(), emailTest2.getId()))
//                .thenReturn(Optional.ofNullable(blockRelationship1));
//
////        assertEquals(statusException, assertThrows(StatusException.class
////                , () -> relationService.addFriend(addCommonRequest)));
//        assertThatThrownBy(() -> relationService.addFriend(addCommonRequest))
//                .isInstanceOf(DataNotFoundException.class)
//                .hasMessage("This email has been blocked !!");
//        Mockito.verify(userRepository).findByEmail(any(String.class));
//        Mockito.verify(relationship).findByEmailIdAndFriendId(any(Long.class), any(Long.class));
//    }

//    @Test
//    public void testAddFriendOldFriend() {
//        statusException = new StatusException("Two Email have already being friend");
//        addCommonRequest = new AddAndGetCommonRequest(Arrays.asList(emailTest1.getEmail(), emailTest2.getEmail()));
//
////        when(emailRepository.findByEmail(emailTest1.getEmail()))
////                .thenReturn(Optional.of(emailTest1));
////        when(emailRepository.findByEmail(emailTest2.getEmail()))
////                .thenReturn(Optional.of(emailTest2));
//        when(relationshipRepository.findByEmailIdAndFriendId
//                (emailTest1.getEmailId(), emailTest2.getEmailId()))
//                .thenReturn(Optional.ofNullable(friendRelationship1));
//
//        assertEquals(statusException, assertThrows(StatusException.class
//                , () -> emailService.addFriend(addCommonRequest)));
//
//    }

    @Test
    public void testGetFriendsSuccess() {
        emailRequest = new RetrieveFriendsListDto.Request(emailTest1.getEmail());

        when(userRepository.findByEmail(emailTest1.getEmail())).thenReturn(Optional.of(emailTest1));
        listEmail = Arrays.asList(emailTest2.getEmail(), emailTest3.getEmail());
        when(userRepository.getListFriendEmails(emailTest1.getId()))
                .thenReturn(listEmail);

        StepVerifier.create(relationService.getFriendList(emailRequest))
                .consumeNextWith(data -> {
                    assertEquals(listEmail.size(), data.size());
                }).verifyComplete();
    }

    @Test
    public void testGetCommonSuccess() {
        addCommonRequest = new AddFriendDto.Request
                (Arrays.asList(emailTest2.getEmail(), emailTest3.getEmail()));
        listEmail = Collections.singletonList(emailTest1.getEmail());

        when(userRepository.findByEmail(emailTest2.getEmail()))
                .thenReturn(Optional.of(emailTest2));
        when(userRepository.findByEmail(emailTest3.getEmail()))
                .thenReturn(Optional.of(emailTest3));
        when(userRepository.getCommonFriends(emailTest2.getId(), emailTest3.getId()))
                .thenReturn(listEmail);

        StepVerifier.create(relationService.getCommonFriends(addCommonRequest))
                .consumeNextWith(data -> {
                    assertEquals(listEmail.size(), data.size());
                }).verifyComplete();

    }

    @Test
    public void testSubscribeSuccess() {
        subBlockRequest = new SubscribeAndBlockDto.Request(emailTest6.getEmail(), emailTest5.getEmail());

        when(userRepository.findByEmail(emailTest6.getEmail())).thenReturn(Optional.of(emailTest6));
        when(userRepository.findByEmail(emailTest5.getEmail())).thenReturn(Optional.of(emailTest5));
        when(relationship.findByEmailIdAndFriendId(emailTest6.getId(), emailTest5.getId()))
                .thenReturn(Optional.empty());

        StepVerifier.create(relationService.subscribeTo(subBlockRequest))
                .consumeNextWith(data -> {
                    assertEquals(true, data.getSuccess());
                }).verifyComplete();
    }

    @Test
    public void testBlockEmailSuccess() {
        subBlockRequest = new SubscribeAndBlockDto.Request(emailTest5.getEmail(), emailTest4.getEmail());

        when(userRepository.findByEmail(emailTest5.getEmail())).thenReturn(Optional.of(emailTest5));
        when(userRepository.findByEmail(emailTest4.getEmail())).thenReturn(Optional.of(emailTest4));
        when(relationship.findById(any())).thenReturn(Optional.empty());

        StepVerifier.create(relationService.blockEmail(subBlockRequest))
                .consumeNextWith(data -> {
                    assertEquals(true, data.getSuccess());
                }).verifyComplete();
    }

    @Test
    public void testRetrieveEmailSuccess() {
        retrieveRequest = new RetrieveEmailsListReceiveUpdateDto.Request(emailTest1.getEmail(), "Hello " + emailTest5.getEmail());
        listEmail = Arrays.asList(emailTest2.getEmail(), emailTest3.getEmail(), emailTest5.getEmail());

        when(userRepository.findByEmail(emailTest1.getEmail())).thenReturn(Optional.of(emailTest1));
        when(userRepository.findByEmail(emailTest5.getEmail())).thenReturn(Optional.of(emailTest5));
        when(userRepository.getRetrievableEmail(any())).thenReturn(listEmail);

        StepVerifier.create(relationService.retrieveEmails(retrieveRequest))
                .consumeNextWith(data -> {
                    assertEquals(new HashSet<>(listEmail), data);
                }).verifyComplete();
    }

}
