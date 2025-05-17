package com.expense.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UsernameAlreadyUsedException extends Exception {
    private final HttpStatus status;
    public UsernameAlreadyUsedException(String message, HttpStatus httpStatus) {
        super(message);
        this.status = httpStatus;
    }
}
