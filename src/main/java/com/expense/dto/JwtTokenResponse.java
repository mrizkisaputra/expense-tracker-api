package com.expense.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class JwtTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expirate;
}
