package com.demo.FriendManagementWebFlux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface AddFriendDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Request {

        @NotNull(message = "List email must not be null or empty")
        @JsonProperty("friends")
        private List<String> friends;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Response {

        private Boolean success;

    }

}
