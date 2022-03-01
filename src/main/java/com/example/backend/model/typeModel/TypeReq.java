package com.example.backend.model.typeModel;

import lombok.Data;

@Data
public class TypeReq {

    private String name;

    //validate is not null
    public boolean isValid() {
        return name != null;
    }

    //validate is blank
    public boolean isBlank() {
        return name.isBlank();
    }
}
