package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class TypeProduct extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "typeProduct", fetch = FetchType.EAGER)
    private List<Product> product;

}

