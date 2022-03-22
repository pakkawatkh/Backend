package com.example.backend.model.adminModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class AShopActiveReq {

    private Boolean active;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid = active == null;
        if (valid) throw MainException.requestInvalid();
    }


}
