package com.expense.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtTokenInvalidException extends Exception {
    private HttpStatus status;
    private String detailMessage;

    public JwtTokenInvalidException(HttpStatus httpStatus) {
        super();
        this.status = httpStatus;
    }

    public JwtTokenInvalidException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
    }

    public JwtTokenInvalidException(HttpStatus httpStatus, String message, String detailMessage) {
        super(message);
        this.status = httpStatus;
        this.detailMessage = detailMessage;
    }
}
