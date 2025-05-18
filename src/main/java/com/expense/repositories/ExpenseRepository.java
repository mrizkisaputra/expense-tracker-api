package com.expense.repositories;

import com.expense.entities.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, String> {
    Optional<Expense> findByIdAndUsersId(String idExpense, String idUser);

    Page<Expense> findAllByUsersId(String idUser, Pageable pageable);
}
