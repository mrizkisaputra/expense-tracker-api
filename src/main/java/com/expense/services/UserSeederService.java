package com.expense.services;

import com.expense.dto.RegisterUserDto;
import com.expense.entities.Role;
import com.expense.entities.RoleEnum;
import com.expense.entities.User;
import com.expense.entities.UserPassword;
import com.expense.repositories.RoleRepository;
import com.expense.repositories.UserPasswordRepository;
import com.expense.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserSeederService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserPasswordRepository userPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public void createRole() {
        List<RoleEnum> rolesName = List.of(RoleEnum.USER, RoleEnum.ADMIN, RoleEnum.SUPER_ADMIN);
        Map<RoleEnum, String> rolesDescription = new HashMap<>();
        rolesDescription.put(RoleEnum.USER, "Default user role");
        rolesDescription.put(RoleEnum.ADMIN, "Administrator role");
        rolesDescription.put(RoleEnum.SUPER_ADMIN, "Super Administrator role");

        rolesName.forEach(roleName -> {
            Optional<Role> optionalRole = roleRepository.findByName(roleName);


            optionalRole.ifPresentOrElse(
                    role -> {
                        System.out.println("Role Found: "+role.getName());
                    },
                    () -> {
                        Role roleEntity = new Role();
                        roleEntity.setName(roleName);
                        roleEntity.setDescription(rolesDescription.get(roleName));
                        roleRepository.save(roleEntity);
                    });
        });
    }

    @Transactional
    public void createSuperAdministrator() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("super.admin@gmail.com");
        registerUserDto.setPassword("superadmin");

        Optional<Role> optionalRole = this.roleRepository.findByName(RoleEnum.SUPER_ADMIN);
        Optional<User> optionalUser = this.userRepository.findByUsername(registerUserDto.getEmail());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User userEntity = new User();
        userEntity.setUsername(registerUserDto.getEmail());
        userEntity.setRole(optionalRole.get());
        User savedUser = userRepository.save(userEntity);

        UserPassword userPasswordEntity = new UserPassword();
        userPasswordEntity.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        userPasswordEntity.setUsers(savedUser);
        userPasswordRepository.save(userPasswordEntity);
    }
}
