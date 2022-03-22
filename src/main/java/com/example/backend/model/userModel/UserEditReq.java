package com.example.backend.model.userModel;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.MainException;
import lombok.Data;

@Data
public class UserEditReq {

    private String firstname;

    private String lastname;

    private String address;

    private String shopName;

    private String phone;

    private Long lat;

    private Long lng;

    private String province;

    private String district;

    private String picture;

    //validate is not null
    public void isValid() throws BaseException {
        boolean valid = firstname == null || lastname == null || address == null || phone == null || lat == null || lng == null || province == null || district == null;

        if (valid) throw MainException.requestInvalid();
    }

    //validate is blank
    public void isBlank() throws BaseException {
        boolean valid = firstname.isBlank() || lastname.isBlank() || address.isBlank() || phone.isBlank() || province.isBlank() || district.isBlank();

        if (valid) throw MainException.requestIsBlank();
    }
}
