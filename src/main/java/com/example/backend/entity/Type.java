package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class Type extends AutoID {

    @Column(nullable = false, length = 60, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "type")
    private List<Orders> orders;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active;
}

