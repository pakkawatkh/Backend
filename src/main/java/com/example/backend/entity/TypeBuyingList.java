package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class TypeBuyingList extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 10)
    private String price;

    @Column(nullable = false)
    private Date date = new Date();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "type_buying_id", nullable = false)
    private TypeBuying buying;

}
