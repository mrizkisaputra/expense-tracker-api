package com.expense.controllers;

import com.expense.dto.*;
import com.expense.entities.Expense;
import com.expense.entities.User;
import com.expense.exceptions.ExpenseNotFoundException;
import com.expense.repositories.ExpenseRepository;
import com.expense.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expenses")
public class ExpenseController {
    private final ExpenseService expenseService;
    private final ExpenseRepository expenseRepository;

    @GetMapping(path = "/{idExpense}", produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetDetailExpense(
            @AuthenticationPrincipal User user,
            @PathVariable String idExpense
    ) throws ExpenseNotFoundException {
        ExpenseResponse expenseResponse = this.expenseService.getDetailExpense(idExpense, user);

        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK).message("success")
                .data(expenseResponse).build();

        return ResponseEntity.ok().body(apiResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> handleCreateExpense(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateExpenseDto newCreateExpense,
            UriComponentsBuilder ucb
    ) {
        String idExpense = this.expenseService.createExpense(newCreateExpense, user);

        // response header location
        URI location = ucb.path("/api/v1/expenses/{idExpense}")
                .buildAndExpand(idExpense)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Object> handleGetAllExpenses(
            @AuthenticationPrincipal User user,
            @Valid @ModelAttribute ExpenseFilterDto expenseFilter,
            Pageable pageable
    ) {
        Page<Expense> page;

        // periksa apakah ada query parameter 'filter'
        if (expenseFilter.getFilter() != null) {
            long now = System.currentTimeMillis();

            // periksa value dari filter itu apa
            Long fromDate = switch (expenseFilter.getFilter().toLowerCase()) {
                case "week" -> now - 7L * 24 * 60 * 60 * 1000; // 7 hari
                case "month" -> now - 30L * 24 * 60 * 60 * 1000; // kira-kira 30 hari
                case "3month" -> now - 90L * 24 * 60 * 60 * 1000; // kira-kira 90 hari
                default -> null;
            };

            // lakukan query dengan filter
            page = this.expenseRepository.findAllByUsersIdAndCreatedAtBetween(user.getId(), fromDate, now, pageable);
            log.info("From Date: {}", fromDate);
        } else {
            // lakukan query tanpa filter
            page = expenseRepository.findAllByUsersId(user.getId(), pageable);
        }

        Paging paging = Paging.builder()
                .totalElement(page.getTotalElements()).totalPage(page.getTotalPages()).size(page.getSize()).build();
        ApiResponse apiResponse = ApiResponse.builder()
                .status(HttpStatus.OK)
                .message("success")
                .data(page.getContent())
                .paging(paging)
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping(path = "/{idExpense}", consumes = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<Void> handleUpdateExpense(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UpdateExpenseDto newExpense,
            @PathVariable String idExpense
    ) throws ExpenseNotFoundException {
        this.expenseService.update(idExpense, user.getId(), newExpense);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{idExpense}")
    private ResponseEntity<Void> handleDeleteExpense(
            @AuthenticationPrincipal User user,
            @PathVariable String idExpense
    ) throws ExpenseNotFoundException {
        this.expenseService.delete(idExpense, user.getId());
        return ResponseEntity.noContent().build();
    }
}
