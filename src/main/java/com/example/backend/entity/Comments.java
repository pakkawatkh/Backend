package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

import static java.lang.Boolean.TRUE;

@Data
@Entity
public class Comments extends AutoID {

    @Column(nullable = false, length = 60)
    private String title;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(nullable = false)
    private Date date = new Date();

    @Column(nullable = false)
    private Boolean active = TRUE;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}