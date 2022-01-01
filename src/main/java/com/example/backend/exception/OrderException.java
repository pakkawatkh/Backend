package com.example.backend.exception;

public class OrderException extends BaseException{
    public OrderException(String code) {
        super("data."+code);
    }

    public static OrderException orderNotFound() {
        return new OrderException("order.not.found");
    }

    public static OrderException accessDenied () {
        return new OrderException("access.denied");
    }

    public static OrderException requestInvalid () {
        return new OrderException("request.invalid");
    }

}
