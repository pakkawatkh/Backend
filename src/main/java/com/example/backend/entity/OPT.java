package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class OPT extends AutoID {

    private Integer ip;

    @Column(length = 15)
    private String phone;

    private Date date;

    @Column(length = 7)
    private String opt;

    @Column(length = 7)
    private String ref;

}
