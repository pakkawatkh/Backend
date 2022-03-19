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

    @Column(length = 30,nullable = false)
    private String name;

    @Column(nullable = false)
    private Date date = new Date();

    @Column(length = 30,nullable = false)
    private String weight;

    @Column(length = 100,nullable = false)
    private String picture;

    @Column(length = 30,nullable = false)
    private String price;

    @Column(nullable = false)
    @Lob
    private String detail;

    @Column(nullable = false)
    private Long lat;

    @Column(nullable = false)
    private Long lng;

    @Column(length = 200,nullable = false)
    private String address;

    @Column(length = 50,nullable = false)
    private String province;

    @Column(length = 30,nullable = false)
    private String district;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public enum Status {
        BUY, CANCEL, SUCCESS
    }
}


