package com.example.backend.exception;

public class TypeException extends BaseException {
    public TypeException(String code) {
        super("type." + code);
    }

    public static TypeException nameDuplicated() {
        return new TypeException("name.duplicated");
    }
    public static TypeException notFoundId() {
        return new TypeException("not.found.id");
    }

    public static TypeException accessDenied(){
        return new TypeException("access.denied");
    }

}
