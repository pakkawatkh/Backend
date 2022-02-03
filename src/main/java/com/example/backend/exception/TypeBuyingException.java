package com.example.backend.exception;

public class TypeBuyingException extends BaseException {
    public TypeBuyingException(String code) {
        super("buying." + code);
    }

    public static TypeBuyingException nameDuplicate() {
        return new TypeBuyingException("name.duplicate");
    }
    public static TypeBuyingException accessDenied() {
        return new TypeBuyingException("access.denied");
    }

    public static TypeBuyingException notAllowed(){
        return new TypeBuyingException("not.allowed");
    }

    public static TypeBuyingException requestInvalid () {
        return new TypeBuyingException("request.invalid");
    }


}
