package com.aacid0.ntf_library.shared.exceptions.email.delivery;

import com.aacid0.ntf_library.shared.exceptions.email.EmailException;

public class EmailDeliveryException extends EmailException {
    public EmailDeliveryException(String message) {
        super(message);
    }
}
