package com.example.backend.entity;

import com.example.backend.entity.Base.RandomID;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column(nullable = false, length = 50)
    private String lastname;

//    @Column(length = 120, unique = true)
//    private String email;

    @JsonIgnore
    @Column(nullable = false, length = 120)
    private String password;

    @Column(nullable = false, length = 15, unique = true)
    private String phone;

    @Column(length = 10, columnDefinition = "varchar(10) default 'USER'",nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private Boolean active;

    @Column(nullable = false)
    private Date date;

    @Column(length = 50)
    private String facebook;

    @Column(length = 50)
    private String line;

    @Column(length = 200)
    private String address;


    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Orders> order;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Shop shop;

    public enum Role {
        ADMIN, USER, SHOP
    }

}

