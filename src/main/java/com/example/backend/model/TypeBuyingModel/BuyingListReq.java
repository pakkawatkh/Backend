package com.example.backend.model.TypeBuyingModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class BuyingListReq {

    private String name;

    private String price;

    private Integer buyingId;

    public void isValid() throws BaseException {
        boolean valid = name == null || price == null || buyingId == null;
        if (valid) throw MainException.requestInvalid();

    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid = name.isBlank();
        if (valid) throw MainException.requestIsBlank();

    }
}
