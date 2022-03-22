package com.example.backend.model.orderModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class OrderReq {

    private String weight;

    private Integer typeId;

    private String picture;

    private String province;

    private String district;

    private String price;

    private String name;

    private String detail;

    private String lat;

    private String lng;

    private String address;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid = typeId == null || picture == null || province == null || district == null || detail == null || name == null ||price == null || weight== null || lat == null || lng== null || address == null;
       if (valid) throw MainException.requestInvalid();

    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid =  picture.isBlank() || province.isBlank() || district.isBlank() || detail.isBlank() || name.isBlank() || price.isBlank() || weight.isBlank()|| address.isBlank();
   if (valid) throw MainException.requestIsBlank();
    }
}
