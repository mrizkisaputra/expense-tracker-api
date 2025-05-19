package com.expense.controllers;

import com.expense.dto.ApiResponse;
import com.expense.dto.UserResponse;
import com.expense.entities.User;
import com.expense.repositories.UserRepository;
import com.expense.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

//    @PreAuthorize("isAuthenticated()")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetAllUsers(@AuthenticationPrincipal User user, Pageable pageable) {
        ApiResponse apiResponse = this.userService.allUsers(pageable);
        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetAuthenticatedUser(@AuthenticationPrincipal User user) {
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId()).email(user.getUsername()).accountNonLocked(user.getAccountNonLocked())
                .accountNonExpired(user.getAccountNonExpired()).accountEnabled(user.getAccountEnabled())
                .createdAt(user.getCreatedAt()).role(user.getRole().getName().toString()).build();

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK).message("success")
                .data(userResponse).build();

        return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
    }
}
