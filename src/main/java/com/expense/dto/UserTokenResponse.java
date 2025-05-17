package com.expense.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data @Builder
public class UserTokenResponse {
    private HttpStatus status;
    private String message;
    private JwtTokenResponse jwt;
}
