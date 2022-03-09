package com.example.backend.entity;

import com.example.backend.entity.Base.AutoID;
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

    @Column()
    @Lob
    private String paragraphOne;


    @Column()
    @Lob
    private String paragraphTwo;


    @Column()
    @Lob
    private String paragraphThree;


    @Column()
    @Lob
    private String paragraphFour;

    @Column()
    @Lob
    private String paragraphFive;

    @Column(length = 100)
    private String pictureOne;

    @Column(length = 100)
    private String pictureTwo;

    @Column(length = 200)
    private String reference;

    @Column()
    @Lob
    private String linkRef;
}
