package com.example.backend.exception;

public class TypeException extends BaseException {
    public TypeException(String code) {
        super(code);
    }

    public static TypeException nameDuplicated() {
        return new TypeException("ชื่อนี้มีผู้ใช้งานแล้ว");
    }
    public static TypeException notFoundId() {
        return new TypeException("ไม่พบประเภทสินค้า");
    }
}
