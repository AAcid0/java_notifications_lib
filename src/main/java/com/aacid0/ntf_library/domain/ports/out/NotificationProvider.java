package com.aacid0.ntf_library.domain.ports.out;

import com.aacid0.ntf_library.domain.model.Notification;

public interface NotificationProvider {
    void send(Notification notification);

    boolean supports(Notification notification);
}
