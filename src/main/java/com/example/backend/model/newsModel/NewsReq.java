package com.example.backend.model.newsModel;

import lombok.Data;

@Data
public class NewsReq {
    private String title;
    private String detail;
    private String picture;
    private String reference;
}
