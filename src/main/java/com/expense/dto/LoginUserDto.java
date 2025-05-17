package com.expense.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginUserDto {
    @NotNull @NotEmpty @Email
    @Size(max = 100)
    private String email;

    @NotNull @NotEmpty
    private String password;
}
