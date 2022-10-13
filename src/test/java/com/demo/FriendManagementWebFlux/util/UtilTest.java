package com.demo.FriendManagementWebFlux.util;

import com.demo.FriendManagementWebFlux.dto.AddFriendDto;
import com.demo.FriendManagementWebFlux.dto.RetrieveEmailsListReceiveUpdateDto;
import com.demo.FriendManagementWebFlux.dto.SubscribeAndBlockDto;
import com.demo.FriendManagementWebFlux.utils.EmailUtils;
import com.demo.FriendManagementWebFlux.utils.RequestValidation;
import com.demo.FriendManagementWebFlux.utils.constraints.ErrorConstraints;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class UtilTest {

        @Data
        @Builder
        static class ExpectData {
            protected String name;
            protected String email;
            protected Boolean isTrue;
            protected Object request;
            protected String error;
        }

//        @Test
//        public void checkCreateFriendConnectionReqSuccess() {
//            List<String> friends = new ArrayList<>();
//            friends.add("testsafa@gmail.com");
//            friends.add("sonsson@gmail.com");
//            AddFriendDto.Request request = new AddFriendDto.Request();
//            Assertions.assertEquals("", RequestValidation.checkAddAndGetCommonRequest(request));
//        }
        @Test
        public void checkCreateFriendConnectionReqNullList() {
            AddFriendDto.Request request = new AddFriendDto.Request(null);
            Assertions.assertEquals(ErrorConstraints.INVALID_REQUEST, RequestValidation.checkAddAndGetCommonRequest(request));
        }
        @Test
        public void checkCreateFriendConnectionReqInvalid() {
            List<String> friends = new ArrayList<>();
            friends.add("testGmail.com");
            friends.add(null);
            AddFriendDto.Request request = new AddFriendDto.Request();
            Assertions.assertEquals(ErrorConstraints.INVALID_REQUEST, RequestValidation.checkAddAndGetCommonRequest(request));
        }
//        @Test
//        public void checkCreateFriendConnectionReqLackEmail() {
//            List<String> friends = new ArrayList<>();
//            friends.add("test@gmail.com");
//            AddFriendDto.Request request = new AddFriendDto.Request();
//            Assertions.assertEquals(ErrorConstraints.INVALID_SIZE, RequestValidation.checkAddAndGetCommonRequest(request));
//        }
//        @Test
//        public void checkCreateFriendConnectionReqInvalidEmail() {
//            List<String> friends = new ArrayList<>();
//            friends.add("testGmail.com");
//            friends.add("son@gmail.com");
//            AddFriendDto.Request request = new AddFriendDto.Request();
//            Assertions.assertEquals(ErrorConstraints.INVALID_EMAIL, RequestValidation.checkAddAndGetCommonRequest(request));
//        }
//        @Test
//        public void checkCreateFriendConnectionReqDuplicateEmail() {
//            List<String> friends = new ArrayList<>();
//            friends.add("test@gmail.com");
//            friends.add("test@gmail.com");
//            AddFriendDto.Request request = new AddFriendDto.Request();
//            Assertions.assertEquals(ErrorConstraints.EMAIL_DUPLICATED, RequestValidation.checkAddAndGetCommonRequest(request));
//        }
        @Test
        public void checkSubscribeAndBlockRequestSuccess() {
            SubscribeAndBlockDto.Request request = new SubscribeAndBlockDto.Request("test@gmail.com", "son@gmail.com");
            Assertions.assertEquals("", RequestValidation.checkSubscribeAndBlockRequest(request));
        }
        @Test
        public void checkSubscribeAndBlockRequestInvalid() {
            SubscribeAndBlockDto.Request request = new SubscribeAndBlockDto.Request(null, "son@gmail.com");
            Assertions.assertEquals(ErrorConstraints.INVALID_REQUEST, RequestValidation.checkSubscribeAndBlockRequest(request));
        }
        @Test
        public void checkSubscribeAndBlockRequestInvalidEmail() {
            SubscribeAndBlockDto.Request request = new SubscribeAndBlockDto.Request("sonGmail.com", "son@gmail.com");
            Assertions.assertEquals(ErrorConstraints.INVALID_EMAIL, RequestValidation.checkSubscribeAndBlockRequest(request));
        }
        @Test
        public void checkSubscribeAndBlockRequestDuplicateEmail() {
            SubscribeAndBlockDto.Request request = new SubscribeAndBlockDto.Request("son@gmail.com", "son@gmail.com");
            Assertions.assertEquals(ErrorConstraints.EMAIL_DUPLICATED, RequestValidation.checkSubscribeAndBlockRequest(request));
        }
        @Test
        public void checkRetrieveRequestSuccess() {
            RetrieveEmailsListReceiveUpdateDto.Request request = new RetrieveEmailsListReceiveUpdateDto.Request("test@gmail.com", "son@gmail.com");
            Assertions.assertEquals("", RequestValidation.checkRetrieveRequest(request));
        }
        @Test
        public void checkRetrieveRequestInvalid() {
            RetrieveEmailsListReceiveUpdateDto.Request request = new RetrieveEmailsListReceiveUpdateDto.Request(null, "hongson@gmail.com");
            Assertions.assertEquals(ErrorConstraints.INVALID_REQUEST, RequestValidation.checkRetrieveRequest(request));
        }
        @Test
        public void checkRetrieveRequestInvalidEmail() {
            RetrieveEmailsListReceiveUpdateDto.Request request = new RetrieveEmailsListReceiveUpdateDto.Request("sonGmail.com", "son@gmail.com");
            Assertions.assertEquals(ErrorConstraints.INVALID_EMAIL, RequestValidation.checkRetrieveRequest(request));
        }
        @Test
        public void getEmailsFromTextTest() {
            String text = "test@gmail.com   hongson@gmail.com";
            Set<String> expect = new HashSet<>();
            expect.add("test@gmail.com");
            expect.add("hongson@gmail.com");
            Set<String> actual = EmailUtils.getEmailsFromText(text);
            Assertions.assertEquals(expect, actual);
        }
        @Test
        public void isEmailTest() {
            List<ExpectData> tcs = new ArrayList<>();
            tcs.addAll(Arrays.asList(new ExpectData.ExpectDataBuilder()
                            .isTrue(true)
                            .email("test@gmail.com")
                            .build(),
                    new ExpectData.ExpectDataBuilder()
                            .isTrue(false)
                            .email("test.com")
                            .build())
            );
            for (ExpectData e: tcs) {
                Assertions.assertEquals(e.isTrue, EmailUtils.isEmail(e.email));
            }
        }
}
