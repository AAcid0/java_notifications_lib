package com.aacid0.ntf_library.shared.exceptions.email.validation;

import com.aacid0.ntf_library.shared.exceptions.email.EmailException;

public class EmptyEmailBodyException extends EmailException {
    public EmptyEmailBodyException() {
        super("El cuerpo del correo no puede estar vacío.");
    }
}
