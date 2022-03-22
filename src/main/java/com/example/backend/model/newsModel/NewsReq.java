package com.example.backend.model.newsModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class NewsReq {
    private String title;
    private String paragraph;
    private String reference;
    private String picture;
    private String linkRef;

    public void isValid() throws BaseException {
        boolean valid = paragraph == null || title == null;
        if (valid) throw MainException.requestInvalid();
    }

    public void isBlank() throws BaseException {
        boolean valid =  paragraph.isBlank() || title.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }

}
