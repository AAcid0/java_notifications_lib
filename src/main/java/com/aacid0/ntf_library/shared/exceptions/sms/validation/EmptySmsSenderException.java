package com.aacid0.ntf_library.shared.exceptions.sms.validation;

import com.aacid0.ntf_library.shared.exceptions.sms.SmsException;

public class EmptySmsSenderException extends SmsException {
    public EmptySmsSenderException() {
        super("El número de teléfono del remitente es obligatorio.");
    }
}
