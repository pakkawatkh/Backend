package com.example.backend.exception;

public class ShopException extends BaseException {
    public ShopException(String code) {
        super("shop." + code);
    }

    public static ShopException notId() {
        return new ShopException("not.find.id");
    }

    public static ShopException accessDenied(){
        return new ShopException("access.denied");
    }

    public static ShopException requestInvalid(){
        return new ShopException("request.invalid");
    }
    public static ShopException registerError(){
        return new ShopException("register.error");
    }
}
