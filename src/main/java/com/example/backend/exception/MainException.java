package com.example.backend.exception;

public class MainException extends BaseException {
    public MainException(String code) {
        super(code);
    }

    public static  MainException requestInvalid() {
        return new MainException("กรุณากรอกข้อมูลให้ครบถ้วน");
    }

    public static  MainException requestIsBlank() {
        return new MainException("กรุณากรอกข้อมูลให้ครบถ้วน");
    }

    public static MainException accessDenied() {
        return new MainException("ไม่อนุญาตให้เข้าถึง");
    }

    public static MainException expires() {
        return new MainException("expires");
    }

    public static MainException errorSave() {
        return new MainException("บันทึกข้อมูลไม่สำเร็จ");
    }
}
