package com.example.backend.exception;

public class ShopException extends BaseException {
    public ShopException(String code) {
        super(code);
    }

    public static ShopException notId() {
        return new ShopException("รหัสร้านค้าไม่ถูกต้อง");
    }

    public static ShopException registerError(){
        return new ShopException("ลงทะเบียนไม่สำเร็จ");
    }

    public static ShopException nameDuplicate(){
        return new ShopException("ชื่อนี้มีผู้ใช้งานแล้ว");
    }
}
