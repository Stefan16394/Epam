package com.epam.engx.cleandesign;

public class WrongZoneTypeException extends RuntimeException {
    public WrongZoneTypeException() {
    }

    public WrongZoneTypeException(String message) {
        super(message);
    }

    public WrongZoneTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongZoneTypeException(Throwable cause) {
        super(cause);
    }

    public WrongZoneTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
