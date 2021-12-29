package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Entity
public class TypeBuying extends AutoID {

    @Column(nullable = false, length = 60)
    private String name;

    @Column(nullable = false, length = 10)
    private Float price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(nullable = false)
    private Date date;

    //    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shop_id", nullable = false)
    private Shop shop;

}
