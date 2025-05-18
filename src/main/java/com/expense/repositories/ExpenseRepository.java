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

    /**
     * select * from expenses
     * where id_user = ? and createdAt >= 7hari and createdAt <= waktu sekarang
     */
    Page<Expense> findAllByUsersIdAndCreatedAtBetween(String idUser, Long fromDate, Long toDate, Pageable pageable);
}
