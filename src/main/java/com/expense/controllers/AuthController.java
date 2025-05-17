package com.expense.controllers;

import com.expense.dto.*;
import com.expense.exceptions.JwtTokenInvalidException;
import com.expense.exceptions.UsernameAlreadyUsedException;
import com.expense.services.AuthService;
import com.expense.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleSignup(@RequestBody @Valid RegisterUserDto request) throws UsernameAlreadyUsedException {
        UserResponse userResponse = authService.signup(request);
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.CREATED).message("signup successful")
                .data(userResponse)
                .build();
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PostMapping(path = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleLogin(@RequestBody @Valid LoginUserDto request) throws JwtTokenInvalidException {
        JwtTokenResponse jwtTokenResponse = authService.authenticate(request);

        UserTokenResponse userTokenResponse = UserTokenResponse.builder()
                .status(HttpStatus.OK).message("login successful")
                .jwt(jwtTokenResponse).build();
        return ResponseEntity.status(userTokenResponse.getStatus()).body(userTokenResponse);
    }
}
