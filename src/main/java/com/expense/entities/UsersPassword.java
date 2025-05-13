package com.expense.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Table(name = "s_password")
@Data @NoArgsConstructor @AllArgsConstructor
public class UsersPassword {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user")
    private String id;

    private String password;

    @OneToOne()
    @MapsId @JsonIgnore
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private Users users;
}
