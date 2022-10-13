package com.demo.FriendManagementWebFlux.repository;


import com.demo.FriendManagementWebFlux.model.RelationId;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import com.demo.FriendManagementWebFlux.repositories.FriendRelationshipRepository;
import com.demo.FriendManagementWebFlux.utils.constraints.FriendStatusEnum;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class FriendRelationshipRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private FriendRelationshipRepository relationshipRepository;

    private UserRelationship relationTest;
    private UserRelationship relationTest2;
    private List<UserRelationship> lisRelations;
    private RelationId userRelationshipId;

    @BeforeAll
    public void setup(){
//        userRelationshipId = RelationId.builder().emailId(1L).friendId(2L).build();
        relationTest = UserRelationship.builder().emailId(1L).friendId(3L).status(FriendStatusEnum.FRIEND.name()).build();
        relationTest2 = UserRelationship.builder().emailId(1L).friendId(12L).status(FriendStatusEnum.FRIEND.name()).build();
    }

    @Test
    public void findByUserRelationship(){
         Optional<UserRelationship> relation = relationshipRepository.findByEmailIdAndFriendId
                (relationTest.getEmailId(), relationTest.getFriendId());

        assertEquals(relationTest.getEmailId(), relation.get().getEmailId());
    }

    @Test
    public void updateStatusSubscribeByEmailIdAndFriendId(){
        int row = relationshipRepository.updateStatusSubscribeByEmailIdAndFriendId
                (relationTest2.getEmailId(), relationTest2.getFriendId());
        assertEquals(1, row);
    }

    @Test
    public void updateStatusFriendByEmailIdAndFriendId(){
        int row = relationshipRepository.updateStatusFriendByEmailIdAndFriendId
                (relationTest.getEmailId(), relationTest.getFriendId());
        assertEquals(1, row);
    }

    @Test
    public void updateStatusByEmailIdAndFriendId(){
        int row = relationshipRepository.updateStatusByEmailIdAndFriendId
                (relationTest.getEmailId(), relationTest.getFriendId());
        assertEquals(1, row);
    }


}
