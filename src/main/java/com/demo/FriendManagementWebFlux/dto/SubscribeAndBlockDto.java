package com.demo.FriendManagementWebFlux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SubscribeAndBlockDto {
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Request {

        @NotEmpty(message = "Requester email must not be empty or null")
        @JsonProperty("requester")
        private String requester;

        @NotEmpty(message = "Target email must not be empty or null")
        @JsonProperty("target")
        private String target;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Response {

        private Boolean success;

    }
}
