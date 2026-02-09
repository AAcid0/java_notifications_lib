package com.aacid0.ntf_library.domain.exceptions.email.delivery;

import com.aacid0.ntf_library.domain.exceptions.email.EmailException;

public class EmailDeliveryException extends EmailException {
    public EmailDeliveryException(String message) {
        super(message);
    }

    public EmailDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
