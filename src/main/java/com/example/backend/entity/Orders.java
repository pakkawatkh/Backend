package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Orders extends AutoID {

    @Column(length = 10, columnDefinition = "varchar(10) default 'BUY'",nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private Date date;

    @Column(length = 10)
    private float weight;

    @Column(length = 100)
    private String picture;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @Column(nullable = false)
    private Long latitude ;

    @Column(nullable = false)
    private Long longitude ;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Status{
        BUY,CANCEL,SUCCESS
    }
}


