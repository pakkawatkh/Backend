package com.example.backend.exception;

public class FileException extends BaseException{
    public FileException(String code) {
        super("file." + code);
    }

    //user.register.email.null
    public static FileException fileNull() {
        return new FileException("null");
    }

    public static FileException fileMaxSiza() {
        return new FileException("max.siza");
    }

    public static FileException unsupported() {
        return new FileException("unsupported.file.type");
    }

}
