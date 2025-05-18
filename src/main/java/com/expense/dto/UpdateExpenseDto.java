package com.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateExpenseDto {
    @NotNull @NotBlank @Size(max = 255)
    private String description;

    @NotNull
    private BigDecimal amount;

    @NotNull @NotBlank
    private String category;
}
