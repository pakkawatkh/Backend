package com.example.backend.exception;

public class OrderException extends BaseException{
    public OrderException(String code) {
        super("data."+code);
    }
}
