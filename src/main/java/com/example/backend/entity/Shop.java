package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Shop extends AutoID {

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String number;

    @Column(nullable = false,columnDefinition = "boolean default true")
    private boolean active;

    @Column(nullable = false)
    private Date date;

    //    @JsonIgnore
    @OneToMany(mappedBy = "orders", fetch = FetchType.EAGER)
    private List<Product> products;

    //    @JsonIgnore
    @OneToMany(mappedBy = "shop", orphanRemoval = true)
    private List<RecycleList> recycledLists;

    private Date dateStart;

    private Date dateEnd;
}


