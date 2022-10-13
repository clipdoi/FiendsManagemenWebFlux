package com.demo.FriendManagementWebFlux.repository;

import com.demo.FriendManagementWebFlux.model.User;
import com.demo.FriendManagementWebFlux.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.*;
import java.util.concurrent.Flow;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    private User emailTest1;
    private User emailTest2;
    List<String> listEmail;

    List<String> listCommon;

    @BeforeAll
    public void setup(){
        emailTest1 = User.builder().id(1L).email("hongson@gmail.com").build();
        emailTest2 = User.builder().id(3L).email("saomai@gmail.com").build();

        listEmail = new ArrayList<>();
        listEmail.add("minhthong@gmail.com");
        listEmail.add("saomai@gmail.com");
        listEmail.add("nguyenquang@gmail.com");
        listEmail.add("nguyenphi@gmail.com");

        listCommon = new ArrayList<>();
        listCommon.add("minhthong@gmail.com");
    }

    @Test
    public void findByEmail(){
        Optional<User> email = userRepository.findByEmail(emailTest1.getEmail());

        assertEquals(email.get().getEmail(), emailTest1.getEmail());
    }

    @Test
    public void getListFriendEmail(){
        List<String> emails = userRepository.getListFriendEmails(emailTest1.getId());

        assertEquals(listEmail, emails);
    }

    @Test
    public void getCommonFriends(){

        List<String> email = userRepository.getCommonFriends(emailTest1.getId(), emailTest2.getId());

        assertEquals(listCommon, email);
    }

    @Test
    public void getRetrievableEmails(){
        List<String> listEmail1 = new ArrayList<>();
        listEmail1.add("minhthong@gmail.com");
        listEmail1.add("saomai@gmail.com");
        listEmail1.add("nguyenquang@gmail.com");
        listEmail1.add("nguyenphi@gmail.com");
        List<String> emails = userRepository.getRetrievableEmail(emailTest1.getId());

        assertEquals(listEmail1, emails);
    }

    @Test
    public void getEmailFromSet(){
        Set<String> setEmails = new HashSet<>(Arrays.asList("saomai@gmail.com", "nguyenquang@gmail.com", "nguyenphi@gmail.com"));

        Set<String> emails = userRepository.getEmailFromSet(setEmails);

        List<String> listEmail1 = new ArrayList<>();
        listEmail1.add("saomai@gmail.com");
        listEmail1.add("nguyenphi@gmail.com");
        listEmail1.add("nguyenquang@gmail.com");
        assertEquals(new HashSet<>(listEmail1), emails);
    }

}
