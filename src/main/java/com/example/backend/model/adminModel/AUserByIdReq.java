package com.example.backend.model.adminModel;

import lombok.Data;

@Data
public class AUserByIdReq {

    private String id;


    //validate is not null
    public boolean isValid() {
        return id != null;
    }
    //validate is blank
    public boolean isBlank() {
        return id.isBlank();
    }

}
