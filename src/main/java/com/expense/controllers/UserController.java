package com.expense.controllers;

import com.expense.dto.ApiResponse;
import com.expense.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetCurrentUser(@AuthenticationPrincipal User user) {
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK).message("success")
                .data(user).build();

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
