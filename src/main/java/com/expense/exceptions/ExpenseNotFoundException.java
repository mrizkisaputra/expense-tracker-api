package com.expense.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpenseNotFoundException extends Exception {
    private final HttpStatus status;

    public ExpenseNotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.status = httpStatus;
    }
}
