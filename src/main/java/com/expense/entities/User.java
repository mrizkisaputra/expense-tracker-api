package com.expense.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity @Table(name = "s_users")
@Data @NoArgsConstructor @AllArgsConstructor
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true, nullable = false, length = 100)
    private String username;

    private Boolean accountNonExpired = true;
    private Boolean accountNonLocked = true;
    private Boolean accountEnabled = true;
    private Long createdAt;

    @OneToOne(mappedBy = "users") @JsonIgnore
    private UserPassword usersPassword;

    @ManyToOne @JsonIgnore
    @JoinColumn(name = "id_role", referencedColumnName = "id")
    private Role role;

    @OneToMany(mappedBy = "users") @JsonIgnore
    private List<Expense> expenses;

    @PrePersist
    public void prePersist() {
        this.createdAt = System.currentTimeMillis();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+role.getName()));
    }

    @Override
    public String getPassword() {
        return this.usersPassword.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled;
    }
}
