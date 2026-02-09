package com.aacid0.ntf_library.shared.exceptions.push.validation;

import com.aacid0.ntf_library.shared.exceptions.push.PushException;

public class EmptyPushBodyException extends PushException {
    public EmptyPushBodyException() {
        super("Por favor incluya un cuerpo para la notificación push.");
    }
}
