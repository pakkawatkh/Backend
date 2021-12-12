package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Orders extends AutoID {


    @Column(nullable = false,length = 10)
    private String number;

    @Column(nullable = false, length = 15)
    private int status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date date;

    //    @JsonIgnore
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<Product> products;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}


