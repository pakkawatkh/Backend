package com.example.backend.exception;

import javax.persistence.MappedSuperclass;
import java.io.IOException;

@MappedSuperclass
public abstract class BaseException extends IOException {
    public BaseException(String code) {
        super(code);
    }
}
