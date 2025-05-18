package com.expense.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class ExpenseResponse {
    private String id;
    private String idUser;
    private String description;
    private BigDecimal amount;
    private String category;
    private Long createdAt;
    private Long updatedAt;
}
