package com.demo.FriendManagementWebFlux.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    @JsonProperty("statusCode")
    private Integer statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonProperty("description")
    private String description;
}
