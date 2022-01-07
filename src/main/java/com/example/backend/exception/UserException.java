package com.example.backend.exception;

public class UserException extends BaseException {
    public UserException(String code) {
        super("user." + code);
    }

    public static UserException notId() {
        return new UserException("not.find.id");
    }

    public static UserException notFound() {
        return new UserException("not.found");
    }

    public static UserException createEmailDuplicated() {
        return new UserException("register.email.duplicated");
    }
    public static UserException createPhoneDuplicated() {
        return new UserException("register.phone.duplicated");
    }

    public static UserException createEmailNull() {
        return new UserException("register.email.null");
    }

    public static UserException createPasswordNull() {
        return new UserException("register.password.null");
    }

    public static UserException createNameNull() {
        return new UserException("register.name.null");
    }

    public static UserException requestInvalid() {
        return new UserException("request.invalid ");
    }

    public static UserException accessDenied() {
        return new UserException("access.denied");
    }
    public static UserException passwordIncorrect () {
        return new UserException("password.incorrect");
    }


}
