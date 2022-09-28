package com.demo.FriendManagementWebFlux.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InputInvalidException extends RuntimeException {

    public InputInvalidException(String message) {
        super(message);
    }

}
