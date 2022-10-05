package com.demo.FriendManagementWebFlux.repositories;

import com.demo.FriendManagementWebFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "select u.email \n" +
            "from user_relationship ur right join public.\"user\" u on ur.friend_id = u.id\n" +
            "where ur.email_id = ?1 and ur.status = 'FRIEND' order by u.id asc", nativeQuery = true)
    List<String> getListFriendEmails(Long id);
}
