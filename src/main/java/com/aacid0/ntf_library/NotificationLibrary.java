package com.aacid0.ntf_library;

import java.util.ArrayList;
import java.util.List;

import com.aacid0.ntf_library.domain.exceptions.NotNullNotificationException;
import com.aacid0.ntf_library.domain.exceptions.NotSupportNotificationException;
import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;

public class NotificationLibrary {
    private final List<NotificationProvider> providers;

    public NotificationLibrary(List<NotificationProvider> providers) {
        this.providers = providers;
    }

    public void send(Notification notification) {
        if (notification == null) {
            throw new NotNullNotificationException("La notificación no puede ser nula.");
        }

        NotificationProvider provider = providers.stream()
                .filter(p -> p.supports(notification))
                .findFirst()
                .orElseThrow(() -> new NotSupportNotificationException(
                        "No se encontró un proveedor que soporte el tipo de notificación."));

        provider.send(notification);
    }

    public static class NotificationLibraryBuilder {
        private List<NotificationProvider> providers = new ArrayList<>();

        public NotificationLibraryBuilder registerProvider(NotificationProvider provider) {
            this.providers.add(provider);
            return this;
        }

        public NotificationLibrary build() {
            return new NotificationLibrary(this.providers);
        }
    }
}
