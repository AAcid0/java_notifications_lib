package com.aacid0.ntf_library.shared.exceptions.sms.validation;

import com.aacid0.ntf_library.shared.exceptions.sms.SmsException;

public class EmptySmsRecipientPhoneException extends SmsException {
    public EmptySmsRecipientPhoneException() {
        super("El número de teléfono del destinatario es obligatorio.");
    }
}
