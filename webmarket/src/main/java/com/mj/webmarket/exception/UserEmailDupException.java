package com.mj.webmarket.exception;

public class UserEmailDupException extends RuntimeException{

    public UserEmailDupException() {
        super();
    }

    public UserEmailDupException(String message) {
        super(message);
    }

    public UserEmailDupException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserEmailDupException(Throwable cause) {
        super(cause);
    }

    protected UserEmailDupException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
