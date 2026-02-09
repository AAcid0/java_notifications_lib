package com.aacid0.ntf_library;

import java.util.ArrayList;
import java.util.List;

import com.aacid0.ntf_library.domain.model.Notification;
import com.aacid0.ntf_library.domain.ports.out.NotificationProvider;

public class NotificationLibrary {
    private final List<NotificationProvider> providers;

    public NotificationLibrary(List<NotificationProvider> providers) {
        this.providers = providers;
    }

    public void send(Notification notification) {
        for (NotificationProvider provider : providers) {
            if (provider.supports(notification)) {
                provider.send(notification);
                break;
            }
        }
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
