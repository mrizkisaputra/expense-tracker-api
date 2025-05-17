package com.expense.services;

import com.expense.dto.JwtTokenResponse;
import com.expense.dto.LoginUserDto;
import com.expense.dto.RegisterUserDto;
import com.expense.dto.UserResponse;
import com.expense.entities.Role;
import com.expense.entities.User;
import com.expense.entities.UserPassword;
import com.expense.exceptions.JwtTokenInvalidException;
import com.expense.exceptions.UsernameAlreadyUsedException;
import com.expense.repositories.RoleRepository;
import com.expense.repositories.UserPasswordRepository;
import com.expense.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserPasswordRepository userPasswordRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Transactional(rollbackFor = {UsernameAlreadyUsedException.class})
    public UserResponse signup(RegisterUserDto request) throws UsernameAlreadyUsedException {
        Boolean exists = userRepository.existsByUsername(request.getEmail());
        if (exists) {
            throw new UsernameAlreadyUsedException("email sudah terpakai", HttpStatus.CONFLICT);
        }

        Role role = new Role();
        role.setId("e65f1f6b-cf0e-4c93-a042-af55a845da79");
        role.setName("USER");

        User userEntity = new User();
        userEntity.setUsername(request.getEmail());
        userEntity.setRole(role);
        User savedUser = userRepository.save(userEntity);

        UserPassword userPasswordEntity = new UserPassword();
        userPasswordEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userPasswordEntity.setUsers(savedUser);
        userPasswordRepository.save(userPasswordEntity);

        return buildUserResponse(savedUser);
    }

    public JwtTokenResponse authenticate(LoginUserDto request) throws JwtTokenInvalidException {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getEmail())
                .orElseThrow();

        // generate jwt token
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRole());
        String token = jwtService.generateToken(user, claims);

        return JwtTokenResponse.builder()
                .accessToken(token).build();
    }

    private UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId()).email(user.getUsername()).role(user.getRole().getName())
                .accountNonExpired(user.getAccountNonExpired()).accountNonLocked(user.getAccountNonLocked())
                .accountEnabled(user.getAccountEnabled()).build();
    }
}
