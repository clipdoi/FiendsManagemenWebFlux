package com.demo.FriendManagementWebFlux.repositories;

import com.demo.FriendManagementWebFlux.model.RelationId;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<UserRelationship, RelationId> {
    Optional<UserRelationship> findByEmailIdAndFriendId(Long emailId, Long friendId);

}
