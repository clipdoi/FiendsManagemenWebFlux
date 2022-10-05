package com.demo.FriendManagementWebFlux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public interface RetrieveFriendsListDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Request {


        @NotEmpty(message = "Email mustn't be empty or null")
        @JsonProperty("email")
        private String email;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Response {

        private Boolean success;
        private List<String> friends;
        private int count;

    }

}
