package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Shop extends AutoID {

    @Column(length = 10)
    private String number;

    @Column(length = 60)
    private String name;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean active;

    @JsonFormat(pattern="dd-MM-yyyy")
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Long latitude;

    @Column(nullable = false)
    private Long longitude;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "shop", orphanRemoval = true)
    private List<TypeBuying> buying;

}


