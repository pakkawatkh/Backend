package com.example.backend.exception;

public class EmailException extends BaseException {
    public EmailException(String code) {
        super(code);
    }

    //user.register.email.null
    public static EmailException templateNotFound() {
        return new EmailException("ไฟล์มีปัญหา");
    }




}
