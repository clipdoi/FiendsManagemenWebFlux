package com.demo.FriendManagementWebFlux.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private Integer statusCode;
    private String message;
    private String timestamp;
    private String description;
}
