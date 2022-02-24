package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

@Data
@Entity
public class News extends AutoID {

    @Column(nullable = false)
    private Boolean status = true;

    @Column(nullable = false)
    private Date date = new Date();

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String detail;

    @Column(length = 100)
    private String picture;

    @Column(length = 200)
    private String reference;
}
