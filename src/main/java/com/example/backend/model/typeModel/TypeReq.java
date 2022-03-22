package com.example.backend.model.typeModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class TypeReq {

    private String name;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid = name == null;
        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid = name.isBlank();
        if (valid) throw MainException.requestIsBlank();
    }
}
