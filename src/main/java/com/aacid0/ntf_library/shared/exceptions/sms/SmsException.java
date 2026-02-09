package com.aacid0.ntf_library.shared.exceptions.sms;

public abstract class SmsException extends RuntimeException {
    public SmsException(String message) {
        super(message);
    }

    public SmsException(String message, Throwable cause) {
        super(message, cause);
    }
}
