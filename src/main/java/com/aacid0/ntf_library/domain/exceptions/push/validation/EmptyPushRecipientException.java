package com.aacid0.ntf_library.domain.exceptions.push.validation;

import com.aacid0.ntf_library.domain.exceptions.push.PushException;

public class EmptyPushRecipientException extends PushException {
    public EmptyPushRecipientException() {
        super("Por favor incluya un destinatario para la notificación push.");
    }
}
