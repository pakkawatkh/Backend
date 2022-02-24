package com.example.backend.exception;

public class NewsException extends BaseException {
    public NewsException(String code) {
        super(code);
    }

    public static NewsException notFound() {
        return new NewsException("รหัสรายการไม่ถูกต้อง");
    }

}
