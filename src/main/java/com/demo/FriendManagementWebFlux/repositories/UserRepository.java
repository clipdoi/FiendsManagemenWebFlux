package com.demo.FriendManagementWebFlux.repositories;

import com.demo.FriendManagementWebFlux.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    Optional<User> findByEmail(String email);

}
