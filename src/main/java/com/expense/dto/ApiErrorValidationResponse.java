package com.expense.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data @Builder
public class ApiErrorValidationResponse extends SubError {
    private String field;
    private String message;
    private Object rejectedValue;
}
