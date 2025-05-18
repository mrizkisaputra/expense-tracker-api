package com.expense.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ExpenseFilterDto {
    @Pattern(regexp = "^(week|month|3month)?$", message = "filter must be 'week', 'month', or '3month'")
    private String filter;
}
