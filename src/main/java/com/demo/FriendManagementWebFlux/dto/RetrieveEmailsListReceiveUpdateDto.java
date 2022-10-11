package com.demo.FriendManagementWebFlux.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

public interface RetrieveEmailsListReceiveUpdateDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Request {

        @NotEmpty(message = "Sender email must not be null or empty")
        @JsonProperty("sender")
        private String sender;

        @NotEmpty(message = "Text must not be null or empty")
        @JsonProperty("text")
        private String text;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    class Response {

        private Boolean success;
        private Set<String> recipients;

    }

}
