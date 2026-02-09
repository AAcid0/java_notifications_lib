package com.aacid0.ntf_library.domain.exceptions.email.validation;

import com.aacid0.ntf_library.domain.exceptions.email.EmailException;

public class EmptyEmailRecipientException extends EmailException {
    public EmptyEmailRecipientException() {
        super("El destinatario del correo no puede estar vacío.");
    }
}
