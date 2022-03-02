package com.example.backend.exception;

public class TypeBuyingException extends BaseException {
    public TypeBuyingException(String code) {
        super(code);
    }

    public static TypeBuyingException nameDuplicate() {
        return new TypeBuyingException("คุณมีประเภทนี้อยู่แล้ว");
    }
    public static TypeBuyingException notFound() {
        return new TypeBuyingException("รหัสประเภทไม่ถูกต้อง");
    }
}
