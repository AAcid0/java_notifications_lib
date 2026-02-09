package com.aacid0.ntf_library.domain.exceptions.push.delivery;

public class PushDeliveryException extends RuntimeException {
    public PushDeliveryException(String message) {
        super(message);
    }

    public PushDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
