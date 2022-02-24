package com.example.backend.exception;

public class UserException extends BaseException {
    public UserException(String code) {
        super(code);
    }

    public static UserException notFound() {
        return new UserException("ไม่มีผู้ใช้นี้ในระบบ");
    }

    public static UserException createPhoneDuplicated() {
        return new UserException("หมายเลขนี้ถูกใช้งานแล้ว");
    }

    public static UserException passwordInvalid() {
        return new UserException("รหัสผ่านไม่ปลอดภัย โปรดใช้รหัสผ่านอื่น");
    }

    public static UserException passwordIncorrect () {
        return new UserException("รหัสผ่านไม่ถึกต้อง");
    }

}
