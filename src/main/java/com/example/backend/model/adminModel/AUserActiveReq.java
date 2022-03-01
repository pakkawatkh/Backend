package com.example.backend.model.adminModel;

import lombok.Data;

@Data
public class AUserActiveReq {

    private Boolean active;

    //validate is not null
    public boolean isValid() {
        return active != null;
    }


}
