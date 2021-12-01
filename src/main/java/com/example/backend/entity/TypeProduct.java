package com.example.backend.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class TypeProduct extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER)
    private List<Product> product;

}

