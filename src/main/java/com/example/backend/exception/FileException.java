package com.example.backend.exception;

public class FileException extends BaseException {
    public FileException(String code) {
        super(code);
    }

    //user.register.email.null
    public static FileException fileNull() {
        return new FileException("ไฟล์มีปัญหา");
    }

    public static FileException fileMaxSize() {
        return new FileException("ไฟล์มีขนาดใหญ่เกินไป");
    }

    public static FileException unsupported() {
        return new FileException("รูปแปปไฟล์ไม่ถูกต้อง");
    }

    public static FileException errorWrite() {
        return new FileException("บันทีกไฟล์ไม่สำเร็จ");
    }


}
