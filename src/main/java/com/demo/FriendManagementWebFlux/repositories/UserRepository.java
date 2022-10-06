package com.demo.FriendManagementWebFlux.repositories;

import com.demo.FriendManagementWebFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query(value = "select u.email \n" +
            "from user_relationship ur right join public.\"user\" u on ur.friend_id = u.id\n" +
            "where ur.email_id = ?1 and ur.status = 'FRIEND' order by u.id asc", nativeQuery = true)
    List<String> getListFriendEmails(Long id);

    @Query(value = "select f1.email \n" +
            "from (select id, email from user_relationship ur right join \"user\" u on ur.friend_id = u.id where ur.email_id = ?1 and status = 'FRIEND') as f1 \n" +
            "inner join (select id from user_relationship ur right join \"user\" u on ur.friend_id = u.id where ur.email_id = ?2 and status = 'FRIEND') as f2 \n" +
            "on f1.id = f2.id;", nativeQuery = true)
    List<String> getCommonFriends(long first_id, long second_id);

    @Query(value = "select email from \"user\" u left join user_relationship ur on u.id = ur.friend_id where ur.email_id = ?1 \n" +
            "and ((ur.status = 'FRIEND' or ur.status = 'SUBSCRIBE') and ur.status != 'BLOCK') order by u.id asc", nativeQuery = true)
    List<String> getRetrievableEmail(Long id);

    @Query(value = "select email \n" +
            "from \"user\" \n" +
            "where email in (?1)", nativeQuery = true)
    Set<String> getEmailFromSet(Set<String> setEmails);
}
