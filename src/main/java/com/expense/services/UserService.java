package com.expense.services;

import com.expense.dto.ApiResponse;
import com.expense.dto.Paging;
import com.expense.entities.RoleEnum;
import com.expense.entities.User;
import com.expense.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ApiResponse allUsers(Pageable pageable) {
        Page<User> allUsersHaveRoleUser = this.userRepository.findAllByRoleName(RoleEnum.USER, pageable);

        Paging paging = Paging.builder()
                .totalElement(allUsersHaveRoleUser.getTotalElements())
                .totalPage(allUsersHaveRoleUser.getTotalPages())
                .size(allUsersHaveRoleUser.getSize())
                .build();
        return ApiResponse.builder()
                .status(HttpStatus.OK).message("success").paging(paging).data(allUsersHaveRoleUser.getContent()).build();
    }
}
