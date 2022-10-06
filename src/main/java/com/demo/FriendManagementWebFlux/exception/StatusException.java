package com.demo.FriendManagementWebFlux.exception;

import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode
public class StatusException extends RuntimeException {

    public StatusException(String message) {
        super(message);
    }

}
