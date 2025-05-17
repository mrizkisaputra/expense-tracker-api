package com.expense;

import com.expense.dto.ApiErrorResponse;
import com.expense.exceptions.JwtTokenInvalidException;
import com.expense.exceptions.UsernameAlreadyUsedException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestExceptionController extends ResponseEntityExceptionHandler {
    @ExceptionHandler({UsernameAlreadyUsedException.class})
    protected ResponseEntity<Object> handleUsernameAlreadyUsed(UsernameAlreadyUsedException ex) {
        return ResponseEntity.status(ex.getStatus()).body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Validation Failed")
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


}
