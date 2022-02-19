package com.example.backend.entity;

import com.example.backend.entity.Base.RandomID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class User extends RandomID {

    @Column(nullable = false, length = 50)
    private String firstname;

    @Column( length = 50)
    private String lastname;

//    @Column(length = 120, unique = true)
//    private String email;

    @Column(length = 100)
    private String picture;

    @JsonIgnore
    @Column(length = 120)
    private String password;

    @Column( length = 15, unique = true)
    private String phone;

    @Column(length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role =Role.USER;

    @Column(nullable = false)
    private Boolean active =TRUE;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date date = new Date();

    @Column(length = 50)
    private String facebook;

    @Column(length = 50)
    private String line;

    @Column(length = 200)
    private String address;

    @Column(length = 50)
    private String socialId;

    @Column(length = 10)
    private Boolean register = FALSE;

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Login login = Login.DEFAULT;

    @JsonIgnore
    @Column(nullable = false)
    private Date last_password = new Date();

    //    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Orders> order;

    @JsonIgnore
    @OneToOne(mappedBy = "user", orphanRemoval = true)
    private Shop shop;

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<Comments> comments;

    public enum Role {ADMIN, USER, SHOP}

    public enum Login {DEFAULT, FACEBOOK, LINE}


}

