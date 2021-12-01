package com.example.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends RandomID {

    public enum Role {
        ADMIN, USER;
    }

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date date;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Product> product;

}

