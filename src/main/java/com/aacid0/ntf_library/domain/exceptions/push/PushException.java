package com.aacid0.ntf_library.domain.exceptions.push;

public abstract class PushException extends RuntimeException {
    public PushException(String message) {
        super(message);
    }

    public PushException(String message, Throwable cause) {
        super(message, cause);
    }
}
