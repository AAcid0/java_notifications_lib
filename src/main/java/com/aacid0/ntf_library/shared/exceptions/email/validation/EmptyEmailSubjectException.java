package com.aacid0.ntf_library.shared.exceptions.email.validation;

import com.aacid0.ntf_library.shared.exceptions.email.EmailException;

public class EmptyEmailSubjectException extends EmailException {
    public EmptyEmailSubjectException() {
        super("Por favor incluya un asunto para el correo.");
    }
}
