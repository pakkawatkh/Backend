package com.example.backend.model.newsModel;

import lombok.Data;

@Data
public class NewsReq {
    private String title;
    private String paragraphOne;
    private String paragraphTwo;
    private String paragraphThree;
    private String paragraphFour;
    private String paragraphFive;
    private String reference;
    private String pictureOne;
    private String pictureTwo;
    private String linkRef;

    public boolean isValid() {
        return paragraphOne != null && title != null;
    }

    public boolean isBlank() {
        return paragraphOne.isBlank() || title.isBlank();
    }

    public boolean isValidPicture() {
        return pictureOne != null && pictureTwo != null;
    }

    public boolean isBlankPicture() {
        return pictureOne.isBlank() || pictureTwo.isBlank();
    }

}
