package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class TypeBuying extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @JsonIgnore
    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(nullable = false)
    private Date date;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

    @JsonIgnore
    @OneToMany(mappedBy = "buying", orphanRemoval = true)
    private List<TypeBuyingList> buyingLists;
}
