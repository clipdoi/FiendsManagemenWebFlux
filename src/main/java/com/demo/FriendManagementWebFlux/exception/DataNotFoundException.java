package com.demo.FriendManagementWebFlux.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DataNotFoundException extends RuntimeException{

    public DataNotFoundException(String message) {
        super(message);
    }

}
