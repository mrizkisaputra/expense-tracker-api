package com.expense;

import com.expense.dto.ApiErrorResponse;
import com.expense.exceptions.ExpenseNotFoundException;
import com.expense.exceptions.JwtTokenInvalidException;
import com.expense.exceptions.RoleNotFoundException;
import com.expense.exceptions.UsernameAlreadyUsedException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UsernameAlreadyUsedException.class})
    protected ResponseEntity<Object> handleUsernameAlreadyUsed(UsernameAlreadyUsedException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(ex.getStatus()).message(ex.getMessage()).build();
        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation
     *
     * @param ex
     * @param headers httpHeaders
     * @param status  httpStatus
     * @param request webRequest
     * @return the ApiErrorResponse Object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        /* mengambil validasi error di level properti/field */
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        /* mengambil validasi error di level class */
        List<ObjectError> objectErrors = ex.getBindingResult().getGlobalErrors();

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Error")
                .build();

        apiErrorResponse.addValidationFieldError(fieldErrors);
        apiErrorResponse.addValidationObjectError(objectErrors);

        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Error")
                .build();
        apiErrorResponse.addValidationError(ex.getConstraintViolations());
        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }

    /**
     * Handle HttpMessageNotReadableException. Triggered when an json request body malformed
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMostSpecificCause().getMessage())
                .build();
        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler({BadCredentialsException.class})
    protected ProblemDetail handleBadCredentials(BadCredentialsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
        problemDetail.setProperty("description", "The username or password is incorrect");
        return problemDetail;
    }

    @ExceptionHandler({JwtTokenInvalidException.class})
    protected ProblemDetail handleJwtTokenInvalid(JwtTokenInvalidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getDetailMessage());
        problemDetail.setProperty("message", ex.getMessage());
        return problemDetail;
    }

    @ExceptionHandler({ExpenseNotFoundException.class})
    protected ResponseEntity<Object> handleExpenseNotFound(ExpenseNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(ex.getStatus()).message(ex.getMessage()).build();

        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }

    @ExceptionHandler({RoleNotFoundException.class})
    protected ResponseEntity<Object> handleRoleNotFound(RoleNotFoundException ex) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(ex.getStatus()).message(ex.getMessage()).build();
        return ResponseEntity.status(apiErrorResponse.getStatus()).body(apiErrorResponse);
    }
}
