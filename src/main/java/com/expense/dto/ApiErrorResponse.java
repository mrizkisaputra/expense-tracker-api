package com.expense.dto;

import jakarta.validation.ConstraintViolation;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ApiErrorResponse {
    private HttpStatus status;
    private String message;
    private List<SubError> subErrors;

    //
    // =================================================================================================================
    public void addValidationFieldError(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::handleValidationError);
    }

    public void addValidationObjectError(List<ObjectError> objectErrors) {
        objectErrors.forEach(this::handleValidationError);
    }

    public void addValidationError(Set<ConstraintViolation<?>> violations) {
        violations.forEach(this::handleValidationError);
    }

    //
    // =================================================================================================================
    private void handleValidationError(FieldError fieldError) {
        this.handleValidationError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue());
    }

    private void handleValidationError(ObjectError objectError) {
        this.handleValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
    }

    private void handleValidationError(ConstraintViolation<?> violation) {
        this.handleValidationError(
                ((PathImpl) violation.getPropertyPath()).getLeafNode().getName(),
                violation.getMessage(),
                violation.getInvalidValue()
        );
    }

    //
    // =================================================================================================================
    private void handleValidationError(String field, String message, Object rejectedValue) {
        ApiErrorValidationResponse apiErrorValidationResponse = ApiErrorValidationResponse.builder()
                .field(field)
                .message(message)
                .rejectedValue(rejectedValue)
                .build();
        this.addSubError(apiErrorValidationResponse);
    }

    private void handleValidationError(String field, String message) {
        ApiErrorValidationResponse apiErrorValidationResponse = ApiErrorValidationResponse.builder()
                .field(field)
                .message(message)
                .build();
        this.addSubError(apiErrorValidationResponse);
    }

    //
    // =================================================================================================================
    private void addSubError(SubError subError) {
        if (this.subErrors == null) {
            this.subErrors = new ArrayList<>();
        }
        this.subErrors.add(subError);
    }
}
