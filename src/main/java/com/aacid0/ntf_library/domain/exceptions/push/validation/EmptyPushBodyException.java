package com.aacid0.ntf_library.domain.exceptions.push.validation;

import com.aacid0.ntf_library.domain.exceptions.push.PushException;

public class EmptyPushBodyException extends PushException {
    public EmptyPushBodyException() {
        super("Por favor incluya un cuerpo para la notificación push.");
    }
}
