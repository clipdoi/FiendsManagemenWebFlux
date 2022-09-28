package com.demo.FriendManagementWebFlux.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class StatusException extends RuntimeException {

    public StatusException(String message) {
        super(message);
    }

}
