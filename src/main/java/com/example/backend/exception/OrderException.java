package com.example.backend.exception;

public class OrderException extends BaseException{
    public OrderException(String code) {
        super(code);
    }

    public static OrderException orderNotFound() {
        return new OrderException("รหัสสินค้าไม่ถูกต้อง");
    }
    public static OrderException notAllowUpdate() {
        return new OrderException("สินค้านี้สิ้นสุดการขายแล้ว");
    }


}
