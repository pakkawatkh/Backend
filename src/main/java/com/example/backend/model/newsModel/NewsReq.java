package com.example.backend.model.newsModel;

import lombok.Data;

@Data
public class NewsReq {
    private String title;
    private String paragraph;
    private String reference;
    private String picture;
    private String linkRef;

    public boolean isValid() {
        return paragraph != null && title != null;
    }

    public boolean isBlank() {
        return paragraph.isBlank() || title.isBlank();
    }

}
