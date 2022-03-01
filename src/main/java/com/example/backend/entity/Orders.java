package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Orders extends AutoID {

    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status = Status.BUY;

    @Column(length = 30)
    private String name;

    @Column(nullable = false)
    private Date date = new Date();

    @Column(length = 10)
    private String weight;

    @Column(length = 100)
    private String picture;

    @Column(length = 5)
    private String price;

    @Column(nullable = false)
    @Lob
    private String detail;

    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

//    @Column(nullable = false)
//    private Long latitude ;
//
//    @Column(nullable = false)
//    private Long longitude ;

    @Column(length = 50)
    private String province;

    @Column(length = 30)
    private String district;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Status {
        BUY, CANCEL, SUCCESS
    }
}


