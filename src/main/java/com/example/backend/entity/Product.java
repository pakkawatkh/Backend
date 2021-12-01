package com.example.backend.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class Product extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 10)
    private String type;

    @Column(nullable = false, length = 10)
    private float price;

    @Column(nullable = false, length = 15)
    private int status;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "type_product_id", nullable = false)
    private TypeProduct typeProduct;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
