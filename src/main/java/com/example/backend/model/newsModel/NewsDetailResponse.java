package com.example.backend.model.newsModel;

import lombok.Data;

import java.util.Date;

@Data
public class NewsDetailResponse {

    private String title;

    private Object paragraph;

    private Object picture;

    private String reference;

    private String linkRef;

    private Date date;

}
