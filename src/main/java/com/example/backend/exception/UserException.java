package com.example.backend.exception;

public class UserException extends BaseException{
    public UserException(String code) {
        super("user."+code);
    }
    public static UserException notId(){
        return new UserException("not.find.id");
    }
}
