package com.aacid0.ntf_library.shared.exceptions.push.validation;

import com.aacid0.ntf_library.shared.exceptions.push.PushException;

public class EmptyPushTitleException extends PushException {
    public EmptyPushTitleException() {
        super("Por favor incluya un título para la notificación push.");
    }
}
