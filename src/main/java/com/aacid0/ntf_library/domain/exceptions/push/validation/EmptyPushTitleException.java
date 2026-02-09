package com.aacid0.ntf_library.domain.exceptions.push.validation;

import com.aacid0.ntf_library.domain.exceptions.push.PushException;

public class EmptyPushTitleException extends PushException {
    public EmptyPushTitleException() {
        super("Por favor incluya un título para la notificación push.");
    }
}
