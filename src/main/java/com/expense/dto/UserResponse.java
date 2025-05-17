package com.expense.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class UserResponse {
    private String id;
    private String email;
    private String role;
    private String createdAt;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean accountEnabled;
}
