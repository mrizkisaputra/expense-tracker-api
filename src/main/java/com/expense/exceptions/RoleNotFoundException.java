package com.expense.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RoleNotFoundException extends Exception {
    private final HttpStatus status;

    public RoleNotFoundException(HttpStatus status) {
        this.status = status;
    }

    public RoleNotFoundException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
