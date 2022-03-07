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
    private String picture;
    private String reference;
    private String linkRef;


    public boolean isValid(){
        return paragraphOne !=null && title!=null&& picture!=null;
    }

    public boolean isBlank(){
        return paragraphOne.isBlank() || title.isBlank()|| picture.isBlank();
    }

}
