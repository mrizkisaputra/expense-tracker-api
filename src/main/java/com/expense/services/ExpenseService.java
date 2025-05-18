package com.expense.services;

import com.expense.dto.CreateExpenseDto;
import com.expense.dto.ExpenseResponse;
import com.expense.dto.UpdateExpenseDto;
import com.expense.entities.Expense;
import com.expense.entities.User;
import com.expense.exceptions.ExpenseNotFoundException;
import com.expense.repositories.ExpenseRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseResponse getDetailExpense(String idExpense, User user) throws ExpenseNotFoundException {
        Expense expense = this.expenseRepository.findByIdAndUsersId(idExpense, user.getId())
                .orElseThrow(() -> new ExpenseNotFoundException(HttpStatus.NOT_FOUND, "expense not found"));

        return buildExpenseResponse(expense);
    }

    public String createExpense(CreateExpenseDto newCreateExpense, User user) {
        Expense entity = new Expense();
        entity.setDescription(newCreateExpense.getDescription());
        entity.setCategory(newCreateExpense.getCategory());
        entity.setAmount(newCreateExpense.getAmount());
        entity.setUsers(user);

        Expense savedExpense = this.expenseRepository.save(entity);
        return savedExpense.getId();
    }

    private ExpenseResponse buildExpenseResponse(Expense expense) {
        return ExpenseResponse.builder()
                .id(expense.getId()).idUser(expense.getUsers().getId()).description(expense.getDescription())
                .category(expense.getCategory()).amount(expense.getAmount()).updatedAt(expense.getUpdatedAt())
                .createdAt(expense.getCreatedAt()).build();
    }

    @Transactional(rollbackFor = {ExpenseNotFoundException.class})
    public void update(String idExpense, String idUser, UpdateExpenseDto newExpense) throws ExpenseNotFoundException {
        Expense expense = this.expenseRepository.findByIdAndUsersId(idExpense, idUser)
                .orElseThrow(() -> new ExpenseNotFoundException(HttpStatus.NOT_FOUND, "expense not found"));

        expense.setDescription(newExpense.getDescription());
        expense.setAmount(newExpense.getAmount());
        expense.setCategory(newExpense.getCategory());

        this.expenseRepository.save(expense);
    }

    @Transactional(rollbackFor = {ExpenseNotFoundException.class})
    public void delete(String idExpense, String idUser) throws ExpenseNotFoundException {
        Expense expense = this.expenseRepository.findByIdAndUsersId(idExpense, idUser)
                .orElseThrow(() -> new ExpenseNotFoundException(HttpStatus.NOT_FOUND, "expense not found"));

        this.expenseRepository.delete(expense);
    }
}
