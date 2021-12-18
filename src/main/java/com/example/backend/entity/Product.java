package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private float price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(length = 10,nullable = true)
    private float weight;

    @ManyToOne
    @JoinColumn(name = "type_product_id", nullable = false)
    private TypeProduct typeProduct;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

}
