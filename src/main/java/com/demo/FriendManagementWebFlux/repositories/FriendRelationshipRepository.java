package com.demo.FriendManagementWebFlux.repositories;

import com.demo.FriendManagementWebFlux.model.RelationId;
import com.demo.FriendManagementWebFlux.model.UserRelationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRelationshipRepository extends JpaRepository<UserRelationship, RelationId> {
    Optional<UserRelationship> findByEmailIdAndFriendId(Long emailId, Long friendId);

    @Modifying
    @Query(value = "UPDATE public.user_relationship\n" +
            "SET status='BLOCK'\n" +
            "WHERE email_id= ?1 AND friend_id= ?2 AND (status = 'FRIEND' or status = 'SUBSCRIBE')", nativeQuery = true)
    int updateStatusByEmailIdAndFriendId(Long emailId, Long friendId);

}
